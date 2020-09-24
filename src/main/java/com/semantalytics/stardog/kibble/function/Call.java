package com.semantalytics.stardog.kibble.function;

import com.complexible.stardog.plan.filter.AbstractExpression;
import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.ValueSolution;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.FunctionDefinition;
import com.complexible.stardog.plan.filter.functions.FunctionRegistry;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Lists;
import com.stardog.stark.IRI;
import com.stardog.stark.Literal;

import java.util.Iterator;
import java.util.List;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.*;
import static java.util.stream.Collectors.toList;

public final class Call extends AbstractExpression implements UserDefinedFunction {

    private final FunctionRegistry functionRegistry = new FunctionRegistry() {

        @Override
        public Iterator<FunctionDefinition> iterator() {
            return iterator();
        }

        @Override
        public FunctionDefinition get(String s) {
            return get(s);
        }

        public FunctionRegistry getInstance() {
            return Instance;
        }

    }.getInstance();

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

        if(getArgs().size() >= 1) {
            final ValueOrError firstArgValueOrError = getFirstArg().evaluate(valueSolution);
            if (!firstArgValueOrError.isError()) {
                final String functionIri;

                if (assertLiteral(firstArgValueOrError.value())) {
                    functionIri = ((Literal) firstArgValueOrError.value()).label();
                } else if (firstArgValueOrError.value() instanceof IRI) {
                    functionIri = firstArgValueOrError.value().toString();
                } else {
                    return ValueOrError.Error;
                }

                final List<Expression> functionArgs = getArgs().stream().skip(1).collect(toList());

                final Expression function;

                if(Compose.compositionMap.containsKey(functionIri)) {
                    function = Compose.get(functionIri, functionArgs);
                } else {
                    function = functionRegistry.get(functionIri, functionArgs, null);
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