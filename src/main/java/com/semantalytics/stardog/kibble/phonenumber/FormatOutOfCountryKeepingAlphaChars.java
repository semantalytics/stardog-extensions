package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

public final class FormatOutOfCountryKeepingAlphaChars extends AbstractFunction implements UserDefinedFunction {

    protected FormatOutOfCountryKeepingAlphaChars() {
        super(2, PhoneNumberVocabulary.formatOutOfCountryKeepingAlphaChars.stringValue());
    }

    private FormatOutOfCountryKeepingAlphaChars(final FormatOutOfCountryKeepingAlphaChars formatOutOfCountryKeepingAlphaChars) {
        super(formatOutOfCountryKeepingAlphaChars);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
      
        return null;
    }

    @Override
    public FormatOutOfCountryKeepingAlphaChars copy() {
        return new FormatOutOfCountryKeepingAlphaChars(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.formatOutOfCountryKeepingAlphaChars.name();
    }
}
