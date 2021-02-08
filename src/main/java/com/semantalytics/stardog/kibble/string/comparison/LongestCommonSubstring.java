package com.semantalytics.stardog.kibble.string.comparison;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

public final class LongestCommonSubstring extends AbstractFunction implements StringFunction {

    static {
        longestCommonSubstring = new org.simmetrics.metrics.LongestCommonSubstring();
    }

    private static final org.simmetrics.metrics.LongestCommonSubstring longestCommonSubstring;

    protected LongestCommonSubstring() {
        super(2, StringMetricVocabulary.longestCommonSubstring.toString());
    }

    private LongestCommonSubstring(final LongestCommonSubstring longestCommonSubsequence) {
        super(longestCommonSubsequence);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {

            final String string1 = ((Literal)values[0]).label();
            final String string2 = ((Literal)values[1]).label();

            return ValueOrError.Float.of(longestCommonSubstring.compare(string1, string2));
        } else {
            return ValueOrError.Error;
        }
    }

    public Function copy() {
        return new LongestCommonSubstring(this);
    }

    public void accept(final ExpressionVisitor expressionVisitor) {
                                                                expressionVisitor.visit(this);
                                                                                                                                                  }

    @Override
    public String toString() {
        return StringMetricVocabulary.longestCommonSubstring.toString();
    }
}

