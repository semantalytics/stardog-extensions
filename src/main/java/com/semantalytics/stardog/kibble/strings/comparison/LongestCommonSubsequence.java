package com.semantalytics.stardog.kibble.strings.comparison;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

public final class LongestCommonSubsequence extends AbstractFunction implements StringFunction {

    static {
        longestCommonSubsequence = new org.simmetrics.metrics.LongestCommonSubsequence();
    }

    private static final org.simmetrics.metrics.LongestCommonSubsequence longestCommonSubsequence;

    protected LongestCommonSubsequence() {
        super(2, StringMetricVocabulary.longestCommonSubsequence.stringValue());
    }

    private LongestCommonSubsequence(final LongestCommonSubsequence longestCommonSubsequence) {
        super(longestCommonSubsequence);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {
            final String firstString = ((Literal)values[0]).label();
            final String secondString = ((Literal)values[1]).label();

            return ValueOrError.Float.of(longestCommonSubsequence.compare(firstString, secondString));
        } else {
            return ValueOrError.Error;
        }
    }

    public Function copy() {
        return new LongestCommonSubsequence(this);
    }

    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.longestCommonSubsequence.name();
    }
}

