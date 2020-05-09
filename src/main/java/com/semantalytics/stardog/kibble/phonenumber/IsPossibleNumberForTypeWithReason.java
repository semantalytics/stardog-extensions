package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;

public final class IsPossibleNumberForTypeWithReason extends AbstractFunction implements UserDefinedFunction {

    protected IsPossibleNumberForTypeWithReason() {
        super(2, PhoneNumberVocabulary.isPossibleNumberForTypeWithReason.stringValue());
    }

    private IsPossibleNumberForTypeWithReason(final IsPossibleNumberForTypeWithReason isPossibleNumberForTypeWithReason) {
        super(isPossibleNumberForTypeWithReason);
    }

    @Override
    protected ValueOrError internalEvaluate(final com.stardog.stark.Value... values) {

        return null;
    }

    @Override
    public IsPossibleNumberForTypeWithReason copy() {
        return new IsPossibleNumberForTypeWithReason(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.isPossibleNumberForTypeWithReason.name();
    }
}
