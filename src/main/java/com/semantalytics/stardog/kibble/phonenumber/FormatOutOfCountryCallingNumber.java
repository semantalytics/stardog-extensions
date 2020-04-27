package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

public final class FormatOutOfCountryCallingNumber extends AbstractFunction implements UserDefinedFunction {

    protected FormatOutOfCountryCallingNumber() {
        super(2, PhoneNumberVocabulary.formatOutOfCountryCallingNumber.stringValue());
    }

    private FormatOutOfCountryCallingNumber(final FormatOutOfCountryCallingNumber formatOutOfCountryCallingNumber) {
        super(formatOutOfCountryCallingNumber);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
      
        return null;
    }

    @Override
    public FormatOutOfCountryCallingNumber copy() {
        return new FormatOutOfCountryCallingNumber(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.formatOutOfCountryCallingNumber.name();
    }
}
