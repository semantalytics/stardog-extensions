package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;

public final class FormatNationalNumberWithPreferredCarrierCode extends AbstractFunction implements UserDefinedFunction {

    protected FormatNationalNumberWithPreferredCarrierCode() {
        super(2, PhoneNumberVocabulary.formatNationalNumberWithPreferredCarrierCode.stringValue());
    }

    private FormatNationalNumberWithPreferredCarrierCode(final FormatNationalNumberWithPreferredCarrierCode formatNationalNumberWithPreferredCarrierCode) {
        super(formatNationalNumberWithPreferredCarrierCode);
    }

    @Override
    protected ValueOrError internalEvaluate(final com.stardog.stark.Value... values) {

        return null;
    }

    @Override
    public FormatNationalNumberWithPreferredCarrierCode copy() {
        return new FormatNationalNumberWithPreferredCarrierCode(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.formatNationalNumberWithPreferredCarrierCode.name();
    }
}
