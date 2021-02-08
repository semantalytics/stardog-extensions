package com.semantalytics.stardog.kibble.function;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.index.dictionary.MappingDictionary;
import com.complexible.stardog.plan.filter.*;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.FunctionDefinition;
import com.complexible.stardog.plan.filter.functions.FunctionRegistry;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Lists;
import com.stardog.stark.IRI;
import com.stardog.stark.Literal;
import com.stardog.stark.Values;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.*;
import static java.util.stream.Collectors.toList;

public final class Filter extends AbstractExpression implements UserDefinedFunction {

    protected Filter() {
        super(new Expression[0]);
    }

    private Filter(final Filter reduce) {
        super(reduce);
    }

    @Override
    public String getName() {
        return FunctionVocabulary.filter.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public Filter copy() {
        return new Filter(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public ValueOrError evaluate(ValueSolution valueSolution) {

        if(getArgs().size() == 2) {
            final ValueOrError firstArgValueOrError = getFirstArg().evaluate(valueSolution);
            if(!firstArgValueOrError.isError()) {
                final String functionIri;

                if (assertLiteral(firstArgValueOrError.value())) {
                    functionIri = ((Literal) firstArgValueOrError.value()).label();
                } else if (firstArgValueOrError.value() instanceof IRI) {
                    functionIri = firstArgValueOrError.toString();
                } else {
                    return ValueOrError.Error;
                }

                final ValueOrError secondArgValueOrError = getSecondArg().evaluate(valueSolution);

                if (!secondArgValueOrError.isError() && assertArrayLiteral(secondArgValueOrError.value())) {

                    final ArrayLiteral elements = (ArrayLiteral) secondArgValueOrError.value();
                    final MappingDictionary dictionary = valueSolution.getDictionary();

                    try {
                        List<ValueOrError> elementResults = Arrays.stream(elements.getValues())
                                .mapToObj(dictionary::getValue)
                                .map(v -> FunctionRegistry.Instance.get(functionIri, Lists.newArrayList(Expressions.constant(v)), null)
                                        .evaluate(valueSolution))
                                .collect(toList());

                        if(elementResults.stream().anyMatch(ValueOrError::isError)) {
                            return ValueOrError.Error;
                        } else {
                            long[] elementResultIds = IntStream.range(0, elements.getValues().length).filter(i -> Literal.booleanValue((Literal)elementResults.get(i).value())).mapToLong(i -> elements.getValues()[i]).toArray();
                            return ValueOrError.General.of(new ArrayLiteral(elementResultIds));
                        }
                    } catch(UnsupportedOperationException e) {
                        return ValueOrError.Error;
                    }
/*
                    try {
                        List<ValueOrError> valueOrErrors = Arrays.stream(((ArrayLiteral) secondArgValueOrError.value()).getValues()).mapToObj(dict::getValue).map(Expressions::constant).map(e -> FunctionRegistry.Instance.get(functionIri, Lists.newArrayList(e), null).evaluate(valueSolution)).collect(toList());

                        return ValueOrError.General.of(new ArrayLiteral(valueOrErrors.stream().filter(v -> EvalUtil.ebv(v).isTrue()).map(ValueOrError::value).mapToLong(dict::add).toArray()));
                    } catch(UnsupportedOperationException e) {
                        return ValueOrError.Error;
                    }

 */

                } else {
                    return ValueOrError.Error;
                }
            } else {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public String toString() {
        return FunctionVocabulary.reduce.toString();
    }
}