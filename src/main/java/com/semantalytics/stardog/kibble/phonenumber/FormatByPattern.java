package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;

public final class FormatByPattern extends AbstractFunction implements UserDefinedFunction {

    private final PhoneNumber phoneNumber = new PhoneNumber();
    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    protected FormatByPattern() {
        super(2, PhoneNumberVocabulary.formatByPattern.stringValue());
    }

    private FormatByPattern(final FormatByPattern formatByPattern) {
        super(formatByPattern);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String number = assertStringLiteral(values[0]).stringValue();
        final String regionCode = assertStringLiteral(values[1]).stringValue();

        return literal(phoneNumberUtil.formatByPattern(number, PhoneNumberFormat.INTERNATIONAL));
    }

    @Override
    public Function copy() {
        return new FormatByPattern(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.formatByPattern.name();
    }
}
