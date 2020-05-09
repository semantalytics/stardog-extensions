package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;

public final class LengthOfNationalDestinationCode extends AbstractFunction implements UserDefinedFunction {

    protected LengthOfNationalDestinationCode() {
        super(2, PhoneNumberVocabulary.lengthOfNationalDestinationCode.stringValue());
    }

    private LengthOfNationalDestinationCode(final LengthOfNationalDestinationCode lengthOfNationalDestinationCode) {
        super(lengthOfNationalDestinationCode);
    }

    @Override
    protected ValueOrError internalEvaluate(final com.stardog.stark.Value... values) {

        return null;
    }

    @Override
    public LengthOfNationalDestinationCode copy() {
        return new LengthOfNationalDestinationCode(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.lengthOfNationalDestinationCode.name();
    }
}
