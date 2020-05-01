package com.semantalytics.stardog.kibble.visualization.ascii;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Value;
import org.openrdf.model.impl.NumericLiteral;

import static com.stardog.stark.Values.literal;

public final class WidthBucket extends AbstractFunction implements StringFunction {

    protected WidthBucket() {
        super(4, AsciiVisualizationVocabulary.widthBucket.stringValue());
    }

    private WidthBucket(final WidthBucket widthBucket) {
        super(widthBucket);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        if(assertNumericLiteral(values[0]) &&
           assertNumericLiteral(values[1]) &&
           assertNumericLiteral(values[2]) &&
           assertNumericLiteral(values[3])) {

            final long value = ((NumericLiteral)values[0]).longValue();
            final long min = ((NumericLiteral)values[1]).longValue();
            final long max = ((NumericLiteral)values[2]).longValue();
            final int bucketCount = ((NumericLiteral)values[3]).intValue();

            if (min > max) {
                return ValueOrError.Error;
            }

            final long bucketWidth = (max - min) / bucketCount;

            final long bucket = Math.floorDiv(value - min, bucketWidth);

            return ValueOrError.General.of(literal(bucket));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public WidthBucket copy() {
        return new WidthBucket(this);
    }

    @Override
    public String toString() {
        return AsciiVisualizationVocabulary.widthBucket.name();
    }
}
