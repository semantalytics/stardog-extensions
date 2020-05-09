package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.stardog.stark.Literal;

import static com.stardog.stark.Values.literal;

public final class IsValidNumberForRegion extends AbstractFunction implements UserDefinedFunction {

    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
    private final PhoneNumber phoneNumber = new PhoneNumber();

    protected IsValidNumberForRegion() {
        super(2, PhoneNumberVocabulary.isValidNumberForRegion.stringValue());
    }

    private IsValidNumberForRegion(final IsValidNumberForRegion isValidNumberForRegion) {
        super(isValidNumberForRegion);
    }

    @Override
    protected ValueOrError internalEvaluate(final com.stardog.stark.Value... values) {

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {
            final String number = ((Literal)values[0]).label();
            final String regionCode = ((Literal)values[1]).label();

            try {
                phoneNumberUtil.parse(number, regionCode, phoneNumber);
            } catch (NumberParseException e) {
                return ValueOrError.Error;
            }

            return ValueOrError.General.of(literal(phoneNumberUtil.isValidNumberForRegion(phoneNumber, regionCode)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Function copy() {
        return new IsValidNumberForRegion(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.isValidNumberForRegion.name();
    }
}
