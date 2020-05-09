package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.stardog.stark.Literal;

import static com.google.i18n.phonenumbers.Phonenumber.*;
import static com.stardog.stark.Values.literal;

public class IsValidNumber extends AbstractFunction implements UserDefinedFunction {

    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
    private final PhoneNumber phoneNumber = new PhoneNumber();

    protected IsValidNumber() {
        super(1, PhoneNumberVocabulary.isValidNumber.stringValue());
    }

    public IsValidNumber(final IsValidNumber isValidNumber) {
        super(isValidNumber);
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
            return ValueOrError.General.of(literal(phoneNumberUtil.isValidNumber(phoneNumber)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public IsValidNumber copy() {
        return new IsValidNumber(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.isValidNumber.name();
    }
}
