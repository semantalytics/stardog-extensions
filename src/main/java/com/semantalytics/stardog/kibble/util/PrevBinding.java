package com.semantalytics.stardog.kibble.util;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Value;

public class PrevBinding extends AbstractFunction implements UserDefinedFunction {

    private Value prev = null;

    protected PrevBinding() {
        super(1, "");
    }

    public PrevBinding(final PrevBinding next) {
        super(next);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        Value curr = prev;
        prev = values[0];

        if(curr == null) {
            return ValueOrError.Error;
        } else {
            return ValueOrError.General.of(curr);
        }
    }

    @Override
    public void initialize() {
        prev = null;
    }

    @Override
    public Function copy() {
        return new PrevBinding(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return "";
    }
}
