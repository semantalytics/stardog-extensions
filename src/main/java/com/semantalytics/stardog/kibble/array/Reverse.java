package com.semantalytics.stardog.kibble.array;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Value;
import org.apache.commons.lang3.ArrayUtils;

public final class Reverse extends AbstractFunction implements UserDefinedFunction {

    private static final String[] theIRIs = new String[] {ArrayVocabulary.reverse.toString()};

    protected Reverse() {
        super(1, theIRIs);
    }

    private Reverse(final Reverse reverse) {
        super(reverse);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if (assertArrayLiteral(values[0])) {
            final long[] ids = ((ArrayLiteral) values[0]).getValues();
            ArrayUtils.reverse(ids);
            return ValueOrError.General.of(new ArrayLiteral(ids));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Reverse copy() {
        return new Reverse(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.reverse.toString();
    }
}
