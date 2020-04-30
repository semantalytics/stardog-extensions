package com.semantalytics.stardog.kibble.string.comparison;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import info.debatty.java.stringsimilarity.MetricLCS;

public class MetricLongestCommonSubsequence extends AbstractFunction implements StringFunction {

    private static final MetricLCS metricLCS = new MetricLCS();

    protected MetricLongestCommonSubsequence() {
        super(2, StringMetricVocabulary.metricLongestCommonSubsequence.stringValue());
    }

    private MetricLongestCommonSubsequence(final MetricLongestCommonSubsequence metricLongestCommonSubsequence) {
        super(metricLongestCommonSubsequence);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {
            final String string1 = ((Literal)values[0]).label();
            final String string2 = ((Literal)values[1]).label();

            return ValueOrError.Double.of(metricLCS.distance(string1, string2));
        } else {
            return ValueOrError.Error;
        }
    }

    public Function copy() {
        return new MetricLongestCommonSubsequence(this);
    }

    public void accept(final ExpressionVisitor expressionVisitor) {
                                                                expressionVisitor.visit(this);
                                                                                                                                                  }

    @Override
    public String toString() {
        return StringMetricVocabulary.metricLongestCommonSubsequence.name();
    }
}

