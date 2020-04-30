package com.semantalytics.stardog.kibble.string.comparison;

import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.Constant;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.simmetrics.metrics.functions.MatchMismatch;

public final class SmithWatermanGotoh extends AbstractFunction implements StringFunction {

    private org.simmetrics.metrics.SmithWatermanGotoh smithWatermanGotoh;

    protected SmithWatermanGotoh() {
        super(Range.closed(2, 5), StringMetricVocabulary.smithWatermanGotoh.stringValue());
    }

    private SmithWatermanGotoh(final SmithWatermanGotoh smithWatermanGotoh) {
        super(smithWatermanGotoh);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1]))  {
            final String firstString = ((Literal)values[0]).label();
            final String secondString = ((Literal)values[1]).label();

            if (smithWatermanGotoh == null) {
                if (values.length == 5) {
                    for (final Expression expression : getArgs()) {
                        //FIXME check only 2 through 7 not all params
                        if (!(expression instanceof Constant)) {
                            return ValueOrError.Error;
                        }
                    }

                    if(assertNumericLiteral(values[2]) && assertNumericLiteral(values[3]) && assertNumericLiteral(values[4])) {

                        final float gapValue = Literal.floatValue((Literal)values[2]);
                        final float matchPenalty = Literal.floatValue((Literal)(values[3]));
                        final float mismatchPenalty = Literal.floatValue((Literal)(values[4]));

                        smithWatermanGotoh = new org.simmetrics.metrics.SmithWatermanGotoh(gapValue, new MatchMismatch(matchPenalty, mismatchPenalty));
                        return ValueOrError.Float.of(smithWatermanGotoh.compare(firstString, secondString));
                    } else {
                        return ValueOrError.Error;
                    }
                } else {
                    smithWatermanGotoh = new org.simmetrics.metrics.SmithWatermanGotoh();
                    return ValueOrError.Float.of(smithWatermanGotoh.compare(firstString, secondString));
                }
            } else {
                return ValueOrError.Error;
            }

        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public void initialize() {
        smithWatermanGotoh = null;
    }

    @Override
    public Function copy() {
        return new SmithWatermanGotoh(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.smithWatermanGotoh.name();
    }
}
