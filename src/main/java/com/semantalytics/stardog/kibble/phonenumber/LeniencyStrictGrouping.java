package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;

public final class LeniencyStrictGrouping extends AbstractFunction implements UserDefinedFunction {

    protected LeniencyStrictGrouping() {
        super(2, PhoneNumberVocabulary.leniencyStrictGrouping.stringValue());
    }

    private LeniencyStrictGrouping(final LeniencyStrictGrouping leniencyStrictGrouping) {
        super(leniencyStrictGrouping);
    }

    @Override
    protected ValueOrError internalEvaluate(final com.stardog.stark.Value... values) {

        return null;
    }

    @Override
    public Function copy() {
        return new LeniencyStrictGrouping(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.leniencyStrictGrouping.name();
    }
}
