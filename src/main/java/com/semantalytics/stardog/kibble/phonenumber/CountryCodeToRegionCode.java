package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;

public final class CountryCodeToRegionCode extends AbstractFunction implements UserDefinedFunction {

    protected CountryCodeToRegionCode() {
        super(2, PhoneNumberVocabulary.countryCodeToRegionCode.stringValue());
    }

    private CountryCodeToRegionCode(final CountryCodeToRegionCode countryCodeToRegionCode) {
        super(countryCodeToRegionCode);
    }

    @Override
    protected ValueOrError internalEvaluate(final com.stardog.stark.Value... values) {

        return null;
    }

    @Override
    public Function copy() {
        return new CountryCodeToRegionCode(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.countryCodeToRegionCode.name();
    }
}
