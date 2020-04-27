package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

public final class CountryCodeToRegionCode extends AbstractFunction implements UserDefinedFunction {

    protected CountryCodeToRegionCode() {
        super(2, PhoneNumberVocabulary.countryCodeToRegionCode.stringValue());
    }

    private CountryCodeToRegionCode(final CountryCodeToRegionCode countryCodeToRegionCode) {
        super(countryCodeToRegionCode);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
      
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
