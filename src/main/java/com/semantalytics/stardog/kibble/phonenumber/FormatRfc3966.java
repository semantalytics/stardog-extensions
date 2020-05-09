package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.stardog.stark.Literal;

import static com.stardog.stark.Values.literal;

public final class FormatRfc3966 extends AbstractFunction implements UserDefinedFunction {

    private final PhoneNumber phoneNumber = new PhoneNumber();
    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    protected FormatRfc3966() {
        super(2, PhoneNumberVocabulary.formatRfc3966.stringValue());
    }

    private FormatRfc3966(final FormatRfc3966 formatRfc3966) {
        super(formatRfc3966);
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

            return ValueOrError.General.of(literal(phoneNumberUtil.format(phoneNumber, PhoneNumberFormat.RFC3966)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public FormatRfc3966 copy() {
        return new FormatRfc3966(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.formatRfc3966.name();
    }
}
