package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

public final class LengthOfGeographicalAreaCode extends AbstractFunction implements UserDefinedFunction {

    protected LengthOfGeographicalAreaCode() {
        super(2, PhoneNumberVocabulary.lengthOfGeographicalAreaCode.stringValue());
    }

    private LengthOfGeographicalAreaCode(final LengthOfGeographicalAreaCode lengthOfGeographicalAreaCode) {
        super(lengthOfGeographicalAreaCode);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
      
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
