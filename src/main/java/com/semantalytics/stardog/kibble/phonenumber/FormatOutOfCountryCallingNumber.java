package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;

public final class FormatOutOfCountryCallingNumber extends AbstractFunction implements UserDefinedFunction {

    protected FormatOutOfCountryCallingNumber() {
        super(2, PhoneNumberVocabulary.formatOutOfCountryCallingNumber.stringValue());
    }

    private FormatOutOfCountryCallingNumber(final FormatOutOfCountryCallingNumber formatOutOfCountryCallingNumber) {
        super(formatOutOfCountryCallingNumber);
    }

    @Override
    protected ValueOrError internalEvaluate(final com.stardog.stark.Value... values) {

        return null;
    }

    @Override
    public FormatOutOfCountryCallingNumber copy() {
        return new FormatOutOfCountryCallingNumber(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.formatOutOfCountryCallingNumber.name();
    }
}
