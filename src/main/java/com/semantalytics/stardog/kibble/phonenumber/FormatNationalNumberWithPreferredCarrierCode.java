package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

public final class FormatNationalNumberWithPreferredCarrierCode extends AbstractFunction implements UserDefinedFunction {

    protected FormatNationalNumberWithPreferredCarrierCode() {
        super(2, PhoneNumberVocabulary.formatNationalNumberWithPreferredCarrierCode.stringValue());
    }

    private FormatNationalNumberWithPreferredCarrierCode(final FormatNationalNumberWithPreferredCarrierCode formatNationalNumberWithPreferredCarrierCode) {
        super(formatNationalNumberWithPreferredCarrierCode);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
      
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
