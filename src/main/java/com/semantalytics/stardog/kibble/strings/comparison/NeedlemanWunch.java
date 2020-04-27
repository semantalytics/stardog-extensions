package com.semantalytics.stardog.kibble.strings.comparison;

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

public final class NeedlemanWunch extends AbstractFunction implements StringFunction {

    private static org.simmetrics.metrics.NeedlemanWunch needlemanWunch;

    protected NeedlemanWunch() {
        super(Range.closed(2, 5), StringMetricVocabulary.needlemanWunch.stringValue());
    }

    private NeedlemanWunch(final NeedlemanWunch needlemanWunch) {
        super(needlemanWunch);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {

            final String firstString = ((Literal)values[0]).label();
            final String secondString = ((Literal)values[1]).label();

            if (values.length == 5) {
                for (final Expression expression : getArgs()) {
                    // FIXME this should only check args 2-4 not all args
                    if (!(expression instanceof Constant)) {
                        return ValueOrError.Error;
                    }
                }
            }

            if (needlemanWunch == null) {
                if (values.length == 5) {

                    if (assertNumericLiteral(values[2]) && assertNumericLiteral(values[3]) && assertNumericLiteral(values[4])) {

                        final float gapValue = Literal.floatValue(((Literal)values[2]));
                        final float subPenaltyA = Literal.floatValue((Literal)values[3]);
                        final float subPenaltyB = Literal.floatValue((Literal)values[4]);

                        needlemanWunch = new org.simmetrics.metrics.NeedlemanWunch(gapValue, new MatchMismatch(subPenaltyA, subPenaltyB));
                    } else {
                        return ValueOrError.Error;
                    }
                } else {
                    needlemanWunch = new org.simmetrics.metrics.NeedlemanWunch();
                }
            }

            return ValueOrError.Float.of(needlemanWunch.compare(firstString, secondString));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Function copy() {
        return new NeedlemanWunch(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.needlemanWunch.name();
    }
}
