package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;

public final class FormatNumberForMobileDialing extends AbstractFunction implements UserDefinedFunction {

    protected FormatNumberForMobileDialing() {
        super(2, PhoneNumberVocabulary.formatNumberForMobileDialing.stringValue());
    }

    private FormatNumberForMobileDialing(final FormatNumberForMobileDialing formatNumberForMobileDialing) {
        super(formatNumberForMobileDialing);
    }

    @Override
    protected ValueOrError internalEvaluate(final com.stardog.stark.Value... values) {

        return null;
    }

    @Override
    public FormatNumberForMobileDialing copy() {
        return new FormatNumberForMobileDialing(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.formatNumberForMobileDialing.name();
    }
}
