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
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String text = assertStringLiteral(values[0]).stringValue();
        final String regionCode = assertStringLiteral(values[1]).stringValue();

        //TODO migrate to pf
        //return literal(phoneNumberUtil.findNumbers(text, regionCode));
        return null;
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
