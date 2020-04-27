package com.semantalytics.stardog.kibble.strings.comparison;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static org.simmetrics.metrics.StringMetrics.*;

public final class OverlapCoefficient extends AbstractFunction implements StringFunction {

    protected OverlapCoefficient() {
        super(2, StringMetricVocabulary.overlapCoefficient.stringValue());
    }

    private OverlapCoefficient(final OverlapCoefficient overlapCoefficient) {
        super(overlapCoefficient);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {

            final String firstString = ((Literal)values[0]).label();
            final String secondString = ((Literal)values[1]).label();

            return ValueOrError.Float.of(overlapCoefficient().compare(firstString, secondString));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public OverlapCoefficient copy() {
        return new OverlapCoefficient(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.overlapCoefficient.name();
    }
}
