package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.stardog.stark.Literal;

import static com.stardog.stark.Values.literal;

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

        if(assertStringLiteral(values[0])) {
            final String number = ((Literal)values[0]).label();

            return ValueOrError.General.of(literal(phoneNumberUtil.isNANPACountry(number)));
        } else {
            return ValueOrError.Error;
        }
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
