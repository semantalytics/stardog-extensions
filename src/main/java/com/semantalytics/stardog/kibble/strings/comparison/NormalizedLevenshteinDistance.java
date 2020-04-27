package com.semantalytics.stardog.kibble.strings.comparison;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

public final class NormalizedLevenshteinDistance extends AbstractFunction implements StringFunction {

    private static final info.debatty.java.stringsimilarity.NormalizedLevenshtein normalizedLevenshtein;

    static {
        normalizedLevenshtein = new info.debatty.java.stringsimilarity.NormalizedLevenshtein();
    }

    protected NormalizedLevenshteinDistance() {
        super(2, StringMetricVocabulary.normalizedLevenshteinDistance.stringValue());
    }

    private NormalizedLevenshteinDistance(final NormalizedLevenshteinDistance normalizedLevenshteinDistance) {
        super(normalizedLevenshteinDistance);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {

            final String firstString = ((Literal)values[0]).label();
            final String secondString = ((Literal)values[1]).label();

            return ValueOrError.Double.of(normalizedLevenshtein.distance(firstString, secondString));
        } else {
            return ValueOrError.Error;
        }
    }

    public Function copy() {
        return new NormalizedLevenshteinDistance(this);
    }

    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.normalizedLevenshteinDistance.name();
    }
}
