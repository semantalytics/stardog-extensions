package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;

public final class CountryCodeForRegion extends AbstractFunction implements UserDefinedFunction {

    protected CountryCodeForRegion() {
        super(2, PhoneNumberVocabulary.countryCodeForRegion.stringValue());
    }

    private CountryCodeForRegion(final CountryCodeForRegion countryCodeForRegion) {
        super(countryCodeForRegion);
    }

    @Override
    protected ValueOrError internalEvaluate(final com.stardog.stark.Value... values) {

        return null;
    }

    @Override
    public Function copy() {
        return new CountryCodeForRegion(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.countryCodeForRegion.name();
    }
}
