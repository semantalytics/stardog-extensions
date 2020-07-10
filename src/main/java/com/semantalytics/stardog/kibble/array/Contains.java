package com.semantalytics.stardog.kibble.array;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.index.dictionary.MappingDictionary;
import com.complexible.stardog.plan.filter.AbstractExpression;
import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.ValueSolution;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Lists;
import com.stardog.stark.Value;

import java.util.Arrays;
import java.util.List;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertArrayLiteral;
import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertLiteral;

public final class Contains extends AbstractExpression implements UserDefinedFunction {

    public Contains() {
        super(new Expression[0]);
    }

    public Contains(final Contains contains) {
        super(contains);
    }

    @Override
    public String getName() {
        return ArrayVocabulary.contains.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public ValueOrError evaluate(final ValueSolution valueSolution) {

        if (getArgs().size() == 2) {
            final ValueOrError firstArgValue = getFirstArg().evaluate(valueSolution);
            if (!firstArgValue.isError() && assertArrayLiteral(firstArgValue.value())) {
                final ArrayLiteral arrayLiteral = (ArrayLiteral) firstArgValue.value();
                final ValueOrError secondArgValueOrError = getSecondArg().evaluate(valueSolution);
                if (!secondArgValueOrError.isError() && assertLiteral(secondArgValueOrError.value())) {

                    final long secondArgId  = valueSolution.getDictionary().add(secondArgValueOrError.value());

                    return ValueOrError.Boolean.of(Arrays.stream(arrayLiteral.getValues()).anyMatch(l -> l == secondArgId));

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
    public Contains copy() {
        return new Contains(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.contains.toString();
    }
}

