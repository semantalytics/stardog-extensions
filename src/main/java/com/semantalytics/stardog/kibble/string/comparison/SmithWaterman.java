package com.semantalytics.stardog.kibble.string.comparison;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.simmetrics.metrics.functions.AffineGap;
import org.simmetrics.metrics.functions.MatchMismatch;

import java.util.Arrays;
import java.util.function.Predicate;

public final class SmithWaterman extends AbstractFunction implements StringFunction {

    private org.simmetrics.metrics.SmithWaterman smithWaterman;

    protected SmithWaterman() {
        super(Range.closed(2, 7), StringMetricVocabulary.smithWaterman.toString());
    }

    private SmithWaterman(final SmithWaterman smithWaterman) {
        super(smithWaterman);
    }

    @Override
    public void initialize() {
        smithWaterman = null;
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {

            final String firstString = ((Literal)values[0]).label();
            final String secondString = ((Literal)values[1]).label();

            switch(values.length) {
                case 7: {
                    final float gapA;
                    final float gapB;
                    final float subPenaltyA;
                    final float subPenaltyB;
                    final int windowSize;

                    if(Arrays.stream(values).skip(2).anyMatch(not(this::assertNumericLiteral))) {
                        return ValueOrError.Error;
                    }

                        gapA = Literal.floatValue((Literal)values[2]);
                        gapB = Literal.floatValue((Literal)values[3]);

                        subPenaltyA = Literal.floatValue((Literal)values[4]);
                        subPenaltyB = Literal.floatValue((Literal)values[5]);

                        windowSize = Literal.intValue((Literal)values[6]);

                    if(smithWaterman == null) {
                        smithWaterman = new org.simmetrics.metrics.SmithWaterman(new AffineGap(gapA, gapB), new MatchMismatch(subPenaltyA, subPenaltyB), windowSize);
                    }
                    break;
                }
                case 2: {
                    if(smithWaterman == null) {
                        smithWaterman = new org.simmetrics.metrics.SmithWaterman();
                    }
                    break;
                }
                default: {
                    return ValueOrError.Error;
                }
            }
            return ValueOrError.Float.of(smithWaterman.compare(firstString, secondString));

        } else {
            return ValueOrError.Error;
        }

    }

    @Override
    public Function copy() {
        return new SmithWaterman(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.smithWaterman.toString();
    }

    private static <R> Predicate<R> not(Predicate<R> predicate) {
        return predicate.negate();
    }
}
