package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;

public final class FormatE164 extends AbstractFunction implements UserDefinedFunction {

    private final PhoneNumber phoneNumber = new PhoneNumber();
    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    protected FormatE164() {
        super(2, PhoneNumberVocabulary.formatE164.stringValue());
    }

    private FormatE164(final FormatE164 formatE164) {
        super(formatE164);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String number = assertStringLiteral(values[0]).stringValue();
        final String regionCode = assertStringLiteral(values[1]).stringValue();

        try {
            phoneNumberUtil.parse(number, regionCode, phoneNumber);
        } catch (NumberParseException e) {
            throw new ExpressionEvaluationException(e);
        }

        return literal(phoneNumberUtil.format(phoneNumber, PhoneNumberFormat.E164));
    }

    @Override
    public Function copy() {
        return new FormatE164(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.formatE164.name();
    }
}
