package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;

public final class NormalizeDigitsOnly extends AbstractFunction implements UserDefinedFunction {

    protected NormalizeDigitsOnly() {
        super(2, PhoneNumberVocabulary.normalizeDigitsOnly.stringValue());
    }

    private NormalizeDigitsOnly(final NormalizeDigitsOnly normalizeDigitsOnly) {
        super(normalizeDigitsOnly);
    }

    @Override
    protected ValueOrError internalEvaluate(final com.stardog.stark.Value... values) {

        return null;
    }

    @Override
    public Function copy() {
        return new NormalizeDigitsOnly(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.normalizeDigitsOnly.name();
    }
}
