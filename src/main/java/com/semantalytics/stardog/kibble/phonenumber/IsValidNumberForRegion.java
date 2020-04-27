package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;

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
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String number = assertStringLiteral(values[0]).stringValue();
        final String regionCode = assertStringLiteral(values[1]).stringValue();

        try {
            phoneNumberUtil.parse(number, regionCode, phoneNumber);
        } catch (NumberParseException e) {
            throw new ExpressionEvaluationException(e);
        }

        return literal(phoneNumberUtil.isValidNumberForRegion(phoneNumber, regionCode));
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
