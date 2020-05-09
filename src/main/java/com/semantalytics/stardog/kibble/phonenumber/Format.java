package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Range;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.stardog.stark.Literal;

import static com.stardog.stark.Values.literal;

public class Format extends AbstractFunction implements UserDefinedFunction {

    private static final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
    private final PhoneNumber phoneNumber = new PhoneNumber();

    protected Format() {
        super(Range.closed(2, 3), PhoneNumberVocabulary.format.stringValue());
    }

    public Format(final Format next) {
        super(next);
    }

    @Override
    protected ValueOrError internalEvaluate(final com.stardog.stark.Value... values) {

        if (assertStringLiteral(values[0])) {

            final String defaultRegion;
            final String number = ((Literal) values[0]).label();
            final PhoneNumberFormat numberFormat;

            if (assertStringLiteral(values[1])) {
                numberFormat = PhoneNumberFormat.valueOf(((Literal) values[1]).label());

                if (values.length == 3 && assertStringLiteral(values[2])) {
                    defaultRegion = ((Literal) values[2]).label();
                } else {
                    defaultRegion = null;
                }

                try {
                    phoneNumberUtil.parse(number, defaultRegion, phoneNumber);

                        switch (numberFormat) {
                            case NATIONAL: {
                                return ValueOrError.General.of(literal(phoneNumberUtil.format(phoneNumber, PhoneNumberFormat.NATIONAL)));
                            }
                            case RFC3966: {
                                return ValueOrError.General.of(literal(phoneNumberUtil.format(phoneNumber, PhoneNumberFormat.RFC3966)));
                            }
                            case E164: {
                                return ValueOrError.General.of(literal(phoneNumberUtil.format(phoneNumber, PhoneNumberFormat.E164)));
                            }
                            case INTERNATIONAL: {
                                return ValueOrError.General.of(literal(phoneNumberUtil.format(phoneNumber, PhoneNumberFormat.INTERNATIONAL)));
                            }
                            default: {
                                return ValueOrError.Error;
                            }
                        }
                } catch (NumberParseException e) {
                    return ValueOrError.Error;
                }
            } else {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Format copy() {
        return new Format(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.format.name();
    }
}
