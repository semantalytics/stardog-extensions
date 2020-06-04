package com.semantalytics.stardog.kibble.array;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Value;

public final class IsEmpty extends AbstractFunction implements UserDefinedFunction {

    private static final String[] theIRIs = new String[] {ArrayVocabulary.isEmpty.toString()};

    protected IsEmpty() {
        super(1, theIRIs);
    }

    private IsEmpty(final IsEmpty distinct) {
        super(distinct);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if (assertArrayLiteral(values[0])) {
            return ValueOrError.Boolean.of(((ArrayLiteral)values[0]).getValues().length == 0);
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public IsEmpty copy() {
        return new IsEmpty(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.isEmpty.name();
    }
}
