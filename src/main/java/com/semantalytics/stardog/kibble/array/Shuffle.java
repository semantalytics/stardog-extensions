package com.semantalytics.stardog.kibble.array;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Value;
import org.apache.commons.lang3.ArrayUtils;

public final class Shuffle extends AbstractFunction implements StringFunction {

    protected Shuffle() {
        super(1, ArrayVocabulary.shuffle.toString());
    }

    private Shuffle(final Shuffle shuffle) {
        super(shuffle);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if (assertArrayLiteral(values[0])) {
            final long[] ids = ((ArrayLiteral) values[0]).getValues();
            ArrayUtils.shuffle(ids);
            return ValueOrError.General.of(new ArrayLiteral(ids));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Shuffle copy() {
        return new Shuffle(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.shuffle.name();
    };
}
