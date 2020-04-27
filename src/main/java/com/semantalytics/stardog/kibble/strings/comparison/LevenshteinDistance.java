package com.semantalytics.stardog.kibble.strings.comparison;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

public final class LevenshteinDistance extends AbstractFunction implements StringFunction {

    private static final info.debatty.java.stringsimilarity.Levenshtein levenshtein;

    static {
        levenshtein = new info.debatty.java.stringsimilarity.Levenshtein();
    }

    protected LevenshteinDistance() {
        super(2, StringMetricVocabulary.levenshteinDistance.stringValue());
    }

    private LevenshteinDistance(final LevenshteinDistance levenshteinDistance) {
        super(levenshteinDistance);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {

            final String string1 = ((Literal)values[0]).label();
            final String string2 = ((Literal)values[1]).label();

            return ValueOrError.Double.of(levenshtein.distance(string1, string2));
        } else {
            return ValueOrError.Error;
        }
    }

    public Function copy() {
        return new LevenshteinDistance(this);
    }

    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.levenshteinDistance.name();
    }
}
