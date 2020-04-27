package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;

public final class IsAlphaNumber extends AbstractFunction implements UserDefinedFunction {

    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    protected IsAlphaNumber() {
        super(1, PhoneNumberVocabulary.isAlphaNumber.stringValue());
    }

    private IsAlphaNumber(final IsAlphaNumber isAlphaNumber) {
        super(isAlphaNumber);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String number = assertStringLiteral(values[0]).stringValue();

        return literal(phoneNumberUtil.isAlphaNumber(number));
    }

    @Override
    public IsAlphaNumber copy() {
        return new IsAlphaNumber(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.isAlphaNumber.name();
    }
}
