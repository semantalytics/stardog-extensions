package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;

public final class LeniencyExactGrouping extends AbstractFunction implements UserDefinedFunction {

    protected LeniencyExactGrouping() {
        super(2, PhoneNumberVocabulary.leniencyExactGrouping.stringValue());
    }

    private LeniencyExactGrouping(final LeniencyExactGrouping leniencyExactGrouping) {
        super(leniencyExactGrouping);
    }

    @Override
    protected ValueOrError internalEvaluate(final com.stardog.stark.Value... values) {

        return null;
    }

    @Override
    public LeniencyExactGrouping copy() {
        return new LeniencyExactGrouping(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.leniencyExactGrouping.name();
    }
}
