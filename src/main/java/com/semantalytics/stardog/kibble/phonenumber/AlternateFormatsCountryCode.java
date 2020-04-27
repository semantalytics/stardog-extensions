package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

public final class AlternateFormatsCountryCode extends AbstractFunction implements UserDefinedFunction {

    protected AlternateFormatsCountryCode() {
        super(2, PhoneNumberVocabulary.alternateFormatsCountryCode.stringValue());
    }

    private AlternateFormatsCountryCode(final AlternateFormatsCountryCode alternateFormatsCountryCode) {
        super(alternateFormatsCountryCode);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
      
        return null;
    }

    @Override
    public Function copy() {
        return new AlternateFormatsCountryCode(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.alternateFormatsCountryCode.name();
    }
}
