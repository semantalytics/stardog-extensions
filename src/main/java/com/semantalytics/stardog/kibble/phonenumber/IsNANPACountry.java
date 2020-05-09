package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

public final class IsNANPACountry extends AbstractFunction implements UserDefinedFunction {

    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    protected IsNANPACountry() {
        super(2, PhoneNumberVocabulary.isNANPACountry.stringValue());
    }

    private IsNANPACountry(final IsNANPACountry isNANPACountry) {
        super(isNANPACountry);
    }

    @Override
    protected ValueOrError internalEvaluate(final com.stardog.stark.Value... values) {

        final String number = assertStringLiteral(values[0]).stringValue();

        return literal(phoneNumberUtil.isNANPACountry(number));
    }

    @Override
    public IsNANPACountry copy() {
        return new IsNANPACountry(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.isNANPACountry.name();
    }
}
