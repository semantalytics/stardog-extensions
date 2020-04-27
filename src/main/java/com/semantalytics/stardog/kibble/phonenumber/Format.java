package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;

public class Format extends AbstractFunction implements UserDefinedFunction {

    private final PhoneNumber phoneNumber = new PhoneNumber();
    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    protected Format() {
        super(3, PhoneNumberVocabulary.format.stringValue());
    }

    public Format(final Format next) {
        super(next);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String number = assertStringLiteral(values[0]).stringValue();
        final String regionCode = assertStringLiteral(values[1]).stringValue();


        try {
            phoneNumberUtil.parse(number, regionCode, phoneNumber);

            switch (PhoneNumberFormat.valueOf(assertStringLiteral(values[2]).stringValue())) {
                case NATIONAL: {
                    return literal(phoneNumberUtil.format(phoneNumber, PhoneNumberFormat.NATIONAL));
                }
                case RFC3966: {
                    return literal(phoneNumberUtil.format(phoneNumber, PhoneNumberFormat.RFC3966));
                }
                case E164: {
                    return literal(phoneNumberUtil.format(phoneNumber, PhoneNumberFormat.E164));
                }
                case INTERNATIONAL: {
                    return literal(phoneNumberUtil.format(phoneNumber, PhoneNumberFormat.INTERNATIONAL));
                }
                default: {
                    throw new ExpressionEvaluationException("Unknown regionCode " + regionCode);
                }
            }
        } catch (NumberParseException e) {
            throw new ExpressionEvaluationException(e);
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
