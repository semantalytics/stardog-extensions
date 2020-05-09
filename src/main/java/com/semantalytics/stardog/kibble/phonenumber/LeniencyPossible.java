package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;

public final class LeniencyPossible extends AbstractFunction implements UserDefinedFunction {

    protected LeniencyPossible() {
        super(2, PhoneNumberVocabulary.leniencyPossible.stringValue());
    }

    private LeniencyPossible(final LeniencyPossible leniencyPossible) {
        super(leniencyPossible);
    }

    @Override
    protected ValueOrError internalEvaluate(final com.stardog.stark.Value... values) {

        return null;
    }

    @Override
    public LeniencyPossible copy() {
        return new LeniencyPossible(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.leniencyPossible.name();
    }
}
