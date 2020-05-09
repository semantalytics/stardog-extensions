package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;

public final class NddPrefixForRegion extends AbstractFunction implements UserDefinedFunction {

    protected NddPrefixForRegion() {
        super(2, PhoneNumberVocabulary.nddPrefixForRegion.stringValue());
    }

    private NddPrefixForRegion(final NddPrefixForRegion nddPrefixForRegion) {
        super(nddPrefixForRegion);
    }

    @Override
    protected ValueOrError internalEvaluate(final com.stardog.stark.Value... values) {

        return null;
    }

    @Override
    public Function copy() {
        return new NddPrefixForRegion(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.nddPrefixForRegion.name();
    }
}
