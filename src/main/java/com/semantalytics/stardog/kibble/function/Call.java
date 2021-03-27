package com.semantalytics.stardog.kibble.function;

import com.complexible.stardog.plan.filter.AbstractExpression;
import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.ValueSolution;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.FunctionDefinition;
import com.complexible.stardog.plan.filter.functions.FunctionRegistry;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.github.davidmoten.geo.Parity;
import com.google.common.collect.Lists;
import com.stardog.stark.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.*;
import static java.util.Collections.*;
import static java.util.stream.Collectors.toList;

public final class Call extends AbstractExpression implements UserDefinedFunction {

    protected Call() {
        super(new Expression[0]);
    }

    private Call(final Call call) {
        super(call);
    }

    @Override
    public String getName() {
        return FunctionVocabulary.call.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public Call copy() {
        return new Call(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public ValueOrError evaluate(final ValueSolution valueSolution) {

        if (getArgs().size() >= 1) {
            final ValueOrError firstArgValueOrError = getFirstArg().evaluate(valueSolution);
            if (!firstArgValueOrError.isError()) {
                final String functionIri;

                if (assertLiteral(firstArgValueOrError.value())) {
                    functionIri = ((Literal) firstArgValueOrError.value()).label();
                } else if (firstArgValueOrError.value() instanceof IRI) {
                    functionIri = firstArgValueOrError.value().toString();
                } else if (firstArgValueOrError.value() instanceof BNode) {
                    functionIri = ((BNode) firstArgValueOrError.value()).id();
                } else {
                    return ValueOrError.Error;
                }

                final List<Expression> functionArgs = getArgs().stream().skip(1).collect(toList());

                Expression function = null;

                try {
                    if (Compose.compositionMap.containsKey(functionIri)) {
                        for (final String f : Compose.compositionMap.get(functionIri)) {
                            if (function == null) {
                                function = FunctionRegistry.Instance.get(f, functionArgs, null);
                            } else {
                                function = FunctionRegistry.Instance.get(f, singletonList(function), null);
                            }
                        }
                    } else if (Partial.partialMap.containsKey(functionIri)) {
                        List<Expression> partialArgs = Partial.partialMap.get(functionIri);
                        List<ValueOrError> partialArgsValueOrError = partialArgs.stream().map(e -> e.evaluate(valueSolution)).collect(toList());
                        if (partialArgsValueOrError.stream().noneMatch(ValueOrError::isError)) {
                            List<String> partialArgsValueString = partialArgsValueOrError.stream().map(ValueOrError::value).map(Value::toString).collect(toList());
                            functionArgs.stream().forEach(e -> {
                                if (partialArgsValueString.indexOf(FunctionVocabulary.var.toString()) != -1) {
                                    partialArgs.set(partialArgsValueString.indexOf(FunctionVocabulary.var.toString()), e);
                                } else {
                                    partialArgs.add(e);
                                }
                            });
                            function = FunctionRegistry.Instance.get(partialArgs.get(0).toString(), partialArgs.stream().skip(1).collect(toList()), null);
                        } else {
                            return ValueOrError.Error;
                        }
                    } else {
                        function = FunctionRegistry.Instance.get(functionIri, functionArgs, null);
                    }
                } catch (UnsupportedOperationException e) {
                    return ValueOrError.Error;
                }

                return function.evaluate(valueSolution);
            } else {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public String toString() {
        return FunctionVocabulary.call.toString();
    }
}