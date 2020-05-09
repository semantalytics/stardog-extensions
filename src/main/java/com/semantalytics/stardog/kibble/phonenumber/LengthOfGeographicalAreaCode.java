package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;

public final class LengthOfGeographicalAreaCode extends AbstractFunction implements UserDefinedFunction {

    protected LengthOfGeographicalAreaCode() {
        super(2, PhoneNumberVocabulary.lengthOfGeographicalAreaCode.stringValue());
    }

    private LengthOfGeographicalAreaCode(final LengthOfGeographicalAreaCode lengthOfGeographicalAreaCode) {
        super(lengthOfGeographicalAreaCode);
    }

    @Override
    protected ValueOrError internalEvaluate(final com.stardog.stark.Value... values) {

        return null;
    }

    @Override
    public LengthOfGeographicalAreaCode copy() {
        return new LengthOfGeographicalAreaCode(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.lengthOfGeographicalAreaCode.name();
    }
}
