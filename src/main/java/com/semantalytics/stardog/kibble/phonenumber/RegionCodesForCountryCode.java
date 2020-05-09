package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;

public final class RegionCodesForCountryCode extends AbstractFunction implements UserDefinedFunction {

    protected RegionCodesForCountryCode() {
        super(2, PhoneNumberVocabulary.regionCodesForCountryCode.stringValue());
    }

    private RegionCodesForCountryCode(final RegionCodesForCountryCode regionCodesForCountryCode) {
        super(regionCodesForCountryCode);
    }

    @Override
    protected ValueOrError internalEvaluate(final com.stardog.stark.Value... values) {

        return null;
    }

    @Override
    public Function copy() {
        return new RegionCodesForCountryCode(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.regionCodesForCountryCode.name();
    }
}
