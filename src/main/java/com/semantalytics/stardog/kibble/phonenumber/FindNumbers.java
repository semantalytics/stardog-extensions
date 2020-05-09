package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.stardog.stark.Literal;

public final class FindNumbers extends AbstractFunction implements UserDefinedFunction {

    private final PhoneNumber phoneNumber = new PhoneNumber();
    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    protected FindNumbers() {
        super(2, PhoneNumberVocabulary.findNumbers.stringValue());
    }

    private FindNumbers(final FindNumbers findNumbers) {
        super(findNumbers);
    }

    @Override
    protected ValueOrError internalEvaluate(final com.stardog.stark.Value... values) {

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {
            final String text = ((Literal)values[0]).label();
            final String regionCode = ((Literal)values[1]).label();

            //TODO migrate to pf
            //return literal(phoneNumberUtil.findNumbers(text, regionCode));
            return null;
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Function copy() {
        return new FindNumbers(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.findNumbers.name();
    }
}
