package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;

public final class FormatOutOfCountryKeepingAlphaChars extends AbstractFunction implements UserDefinedFunction {

    protected FormatOutOfCountryKeepingAlphaChars() {
        super(2, PhoneNumberVocabulary.formatOutOfCountryKeepingAlphaChars.stringValue());
    }

    private FormatOutOfCountryKeepingAlphaChars(final FormatOutOfCountryKeepingAlphaChars formatOutOfCountryKeepingAlphaChars) {
        super(formatOutOfCountryKeepingAlphaChars);
    }

    @Override
    protected ValueOrError internalEvaluate(final com.stardog.stark.Value... values) {

        return null;
    }

    @Override
    public FormatOutOfCountryKeepingAlphaChars copy() {
        return new FormatOutOfCountryKeepingAlphaChars(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.formatOutOfCountryKeepingAlphaChars.name();
    }
}
