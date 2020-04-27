package com.semantalytics.stardog.kibble.visualization.ascii;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.*;

public final class WidthBucket extends AbstractFunction implements StringFunction {

    protected WidthBucket() {
        super(4, AsciiVisualizationVocabulary.widthBucket.stringValue());
    }

    private WidthBucket(final WidthBucket widthBucket) {
        super(widthBucket);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final long value = assertNumericLiteral(values[0]).longValue();
        final long min = assertNumericLiteral(values[1]).longValue();
        final long max = assertNumericLiteral(values[2]).longValue();
        final int bucketCount = assertNumericLiteral(values[3]).intValue();

        if(min > max) {
            throw new ExpressionEvaluationException("Max must be greater than Min. Found Max: " + max + ", Min: " + min);
        }

        final long bucketWidth = (max - min) / bucketCount;

        final long bucket = Math.floorDiv(value - min, bucketWidth);

        return literal(bucket);
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
