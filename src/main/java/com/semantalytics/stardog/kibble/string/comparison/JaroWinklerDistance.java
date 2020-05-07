package com.semantalytics.stardog.kibble.string.comparison;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

public final class JaroWinklerDistance extends AbstractFunction implements StringFunction {

    protected JaroWinklerDistance() {
        super(Range.closed(2, 5), StringMetricVocabulary.jaroWinklerDistance.stringValue());
    }

    private JaroWinklerDistance(final JaroWinklerDistance jaroWinklerDistance) {
        super(jaroWinklerDistance);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {

            final String firstString = ((Literal)values[0]).label();
            final String secondString = ((Literal)values[1]).label();

            float boostThreshold = 0.7f;
            float prefixScale = 0.1f;
            int maxPrefixLength = 4;

            if(values.length >= 3) {
                if(assertNumericLiteral(values[2])) {
                    boostThreshold = Literal.floatValue((Literal)values[2]);
                } else {
                    return ValueOrError.Error;
                }
            }
            if(values.length >= 4) {
                if(assertNumericLiteral(values[3])) {
                    prefixScale = Literal.floatValue((Literal)values[3]);
                } else {
                    return ValueOrError.Error;
                }
            }
            if(values.length == 5) {
                if(assertNumericLiteral(values[4])) {
                    maxPrefixLength = Literal.intValue((Literal)values[4]);
                } else {
                    return ValueOrError.Error;
                }
            }

            final org.simmetrics.metrics.JaroWinkler jaroWinkler;
            jaroWinkler = new org.simmetrics.metrics.JaroWinkler(boostThreshold, prefixScale, maxPrefixLength);

            return ValueOrError.Float.of(jaroWinkler.distance(firstString, secondString));
        } else {
            return ValueOrError.Error;
        }
    }

    public Function copy() {
        return new JaroWinklerDistance(this);
    }

    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.jaroWinklerDistance.name();
    }
}
