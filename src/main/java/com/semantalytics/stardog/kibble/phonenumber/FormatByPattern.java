package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Lists;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.stardog.stark.Literal;

import static com.stardog.stark.Values.literal;

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
    protected ValueOrError internalEvaluate(final com.stardog.stark.Value... values) {

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {
            final String number = ((Literal)values[0]).label();
            final String regionCode = ((Literal)values[1]).label();

            final PhoneNumber phoneNumber;
            try {
                phoneNumber = phoneNumberUtil.parse(number, regionCode);
            } catch (NumberParseException e) {
               return ValueOrError.Error;
            }

            return ValueOrError.General.of(literal(phoneNumberUtil.formatByPattern(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL, Lists.newArrayList())));
        } else {
            return ValueOrError.Error;
        }
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
