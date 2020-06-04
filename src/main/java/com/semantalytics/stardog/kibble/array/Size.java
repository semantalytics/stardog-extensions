package com.semantalytics.stardog.kibble.array;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Value;

public final class Size extends AbstractFunction implements UserDefinedFunction {

    protected Size() {
        super(1, ArrayVocabulary.size.toString());
    }

    private Size(final Size size) {
        super(size);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if (assertArrayLiteral(values[0])) {
            return ValueOrError.Int.of(((ArrayLiteral) values[0]).getValues().length);
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Size copy() {
        return new Size(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.size.name();
    }
}
