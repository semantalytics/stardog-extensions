package com.semantalytics.stardog.kibble.string.comparison;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.base.Objects;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.util.HashMap;
import java.util.Map;

public final class WeightedLevenshteinDistance extends AbstractFunction implements StringFunction {

    private info.debatty.java.stringsimilarity.WeightedLevenshtein weightedLevenshtein;

    protected WeightedLevenshteinDistance() {
        super(Range.atLeast(2), StringMetricVocabulary.weightedLevenshteinDistance.toString());
    }

    private WeightedLevenshteinDistance(final WeightedLevenshteinDistance weightedLevenshteinDistance) {
        super(weightedLevenshteinDistance);
    }

    public Function copy() {
        return new WeightedLevenshteinDistance(this);
    }

    @Override
    public void initialize() {
        weightedLevenshtein = null;
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {

            final String string1 = ((Literal)values[0]).label();
            final String string2 = ((Literal)values[1]).label();

            if ((values.length - 2) % 3 != 0) {
                return ValueOrError.Error;
            }

            for (int i = 2; i < values.length; i += 3) {
                if(!assertStringLiteral(values[i]) || !assertStringLiteral(values[i + 1]) || !assertNumericLiteral(values[i + 2])) {
                    return ValueOrError.Error;
                }
            }
            if(weightedLevenshtein == null) {
                Map<SubstitutionPair, Double> characterSubstitutionMap = new HashMap<>();

                for (int i = 2; i < values.length; i += 3) {

                    final Character character1 = ((Literal)values[i]).label().charAt(0);
                    final Character character2 = ((Literal)values[i + 1]).label().charAt(0);
                    final SubstitutionPair substitutionPair = new SubstitutionPair(character1, character2);
                    final Double weight = Literal.doubleValue((Literal)values[i + 2]);

                    characterSubstitutionMap.put(substitutionPair, weight);
                }

                weightedLevenshtein = new info.debatty.java.stringsimilarity.WeightedLevenshtein(
                        (char c1, char c2) -> {
                            SubstitutionPair substitutionPair = new SubstitutionPair(c1, c2);
                            if (characterSubstitutionMap.containsKey(substitutionPair)) {
                                return characterSubstitutionMap.get(substitutionPair);
                            } else {
                                return 1.0;
                            }
                        }
                );

            }

            return ValueOrError.Double.of(weightedLevenshtein.distance(string1, string2));
        } else {
            return ValueOrError.Error;
        }
    }

    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.weightedLevenshteinDistance.toString();
    }

    private class SubstitutionPair {

        private final char c1;
        private final char c2;

        SubstitutionPair(final char c1, final char c2) {
            this.c1 = c1;
            this.c2 = c2;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(c1, c2);
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }

            if (object instanceof SubstitutionPair) {
                SubstitutionPair other = (SubstitutionPair) object;

                return Objects.equal(c1, other.c1) && Objects.equal(c2, other.c2);
            }
            return false;
        }
    }
}
