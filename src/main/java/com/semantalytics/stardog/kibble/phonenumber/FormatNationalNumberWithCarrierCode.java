package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

public final class FormatNationalNumberWithCarrierCode extends AbstractFunction implements UserDefinedFunction {

    protected FormatNationalNumberWithCarrierCode() {
        super(2, PhoneNumberVocabulary.formatNationalNumberWithCarrierCode.stringValue());
    }

    private FormatNationalNumberWithCarrierCode(final FormatNationalNumberWithCarrierCode formatNationalNumberWithCarrierCode) {
        super(formatNationalNumberWithCarrierCode);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
      
        return null;
    }

    @Override
    public Function copy() {
        return new FormatNationalNumberWithCarrierCode(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.formatNationalNumberWithCarrierCode.name();
    }
}
