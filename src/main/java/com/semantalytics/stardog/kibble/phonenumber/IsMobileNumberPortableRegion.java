package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.stardog.stark.Literal;

import static com.stardog.stark.Values.literal;

public final class IsMobileNumberPortableRegion extends AbstractFunction implements UserDefinedFunction {

    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    protected IsMobileNumberPortableRegion() {
        super(1, PhoneNumberVocabulary.isMobileNumberPortableRegion.stringValue());
    }

    private IsMobileNumberPortableRegion(final IsMobileNumberPortableRegion isMobileNumberPortableRegion) {
        super(isMobileNumberPortableRegion);
    }

    @Override
    protected ValueOrError internalEvaluate(final com.stardog.stark.Value... values) {

        if(assertStringLiteral(values[0])) {
            final String number = ((Literal) values[0]).label();

            return ValueOrError.General.of(literal(phoneNumberUtil.isMobileNumberPortableRegion(number)));
        } else {
            return ValueOrError.Error;
        }
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
