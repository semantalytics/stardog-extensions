package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;

public final class IsMobileNumberPortableRegion extends AbstractFunction implements UserDefinedFunction {

    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    protected IsMobileNumberPortableRegion() {
        super(1, PhoneNumberVocabulary.isMobileNumberPortableRegion.stringValue());
    }

    private IsMobileNumberPortableRegion(final IsMobileNumberPortableRegion isMobileNumberPortableRegion) {
        super(isMobileNumberPortableRegion);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String number = assertStringLiteral(values[0]).stringValue();

        return literal(phoneNumberUtil.isMobileNumberPortableRegion(number));
    }

    @Override
    public IsMobileNumberPortableRegion copy() {
        return new IsMobileNumberPortableRegion(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.isMobileNumberPortableRegion.name();
    }
}
