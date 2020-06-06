package com.semantalytics.stardog.kibble.util;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

public class Is extends AbstractFunction implements UserDefinedFunction {

    public Is() {
        super(1, UtilVocabulary.is.stringValue());
    }

    private Is(final Is is) {
        super(is);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
            return ValueOrError.General.of(values[0]);
    }

    @Override
    public Is copy() {
        return new Is(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return UtilVocabulary.is.name();
    }
}
