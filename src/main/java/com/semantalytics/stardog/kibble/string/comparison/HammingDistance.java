package com.semantalytics.stardog.kibble.string.comparison;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.google.common.base.Preconditions.checkArgument;

public final class HammingDistance extends AbstractFunction implements StringFunction {

    protected HammingDistance() {
        super(2, StringMetricVocabulary.hammingDistance.toString());
    }

    private HammingDistance(final HammingDistance hammingDistance) {
        super(hammingDistance);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {
            final String string1 = ((Literal)values[0]).label();
            final String string2 = ((Literal)values[1]).label();

            if(string1.length() != string2.length()) {
                return ValueOrError.Error;
            }

            return ValueOrError.Double.of(distance(string1, string2));

        } else {
            return ValueOrError.Error;
        }
    }

    private double distance(final String a, final String b) {
        checkArgument(a.length() == b.length());

        if (a.isEmpty()) {
            return 0;
        }

        int distance = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }

    @Override
    public Function copy() {
        return new HammingDistance(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.hammingDistance.toString();
    }
}
