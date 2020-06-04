package com.semantalytics.stardog.kibble.array;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Value;
import org.apache.commons.lang.ArrayUtils;

public final class Equals extends AbstractFunction implements UserDefinedFunction {

    private static final String[] theIRIs = new String[] {ArrayVocabulary.equals.toString()};

    protected Equals() {
        super(2, theIRIs);
    }

    private Equals(final Equals equals) {
        super(equals);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if (assertArrayLiteral(values[0]) && assertArrayLiteral(values[1])) {
            final long[] idsFirst = ((ArrayLiteral) values[0]).getValues();
            final long[] idsSecond = ((ArrayLiteral) values[1]).getValues();

            return ValueOrError.Boolean.of(ArrayUtils.isEquals(idsFirst, idsSecond));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Equals copy() {
        return new Equals(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.equals.name();
    }
}
