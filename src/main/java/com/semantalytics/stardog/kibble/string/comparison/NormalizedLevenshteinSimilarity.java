package com.semantalytics.stardog.kibble.string.comparison;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

public final class NormalizedLevenshteinSimilarity extends AbstractFunction implements StringFunction {

    private static final info.debatty.java.stringsimilarity.NormalizedLevenshtein normalizedLevenshtein;

    static {
        normalizedLevenshtein = new info.debatty.java.stringsimilarity.NormalizedLevenshtein();
    }

    protected NormalizedLevenshteinSimilarity() {
        super(2, StringMetricVocabulary.normalizedLevenshteinSimarity.toString());
    }

    private NormalizedLevenshteinSimilarity(final NormalizedLevenshteinSimilarity normalizedLevenshteinSimilarity) {
        super(normalizedLevenshteinSimilarity);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {

            final String firstString = ((Literal)values[0]).label();
            final String secondString = ((Literal)values[1]).label();

            return ValueOrError.Double.of(normalizedLevenshtein.similarity(firstString, secondString));
        } else {
            return ValueOrError.Error;
        }
    }

    public NormalizedLevenshteinSimilarity copy() {
        return new NormalizedLevenshteinSimilarity(this);
    }

    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.normalizedLevenshteinSimarity.toString();
    }
}
