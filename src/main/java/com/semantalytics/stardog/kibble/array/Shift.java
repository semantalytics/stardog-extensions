package com.semantalytics.stardog.kibble.array;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.apache.commons.lang3.ArrayUtils;

public final class Shift extends AbstractFunction implements StringFunction {

    protected Shift() {
        super(2, ArrayVocabulary.shift.toString());
    }

    private Shift(final Shift shift) {
        super(shift);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if (assertArrayLiteral(values[0]) && assertNumericLiteral(values[1])) {
            final long[] ids = ((ArrayLiteral) values[0]).getValues();
            final int offset = Literal.intValue((Literal) values[1]);
            ArrayUtils.shift(ids, offset);
            return ValueOrError.General.of(new ArrayLiteral(ids));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Shift copy() {
        return new Shift(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.shift.toString();
    };
}
