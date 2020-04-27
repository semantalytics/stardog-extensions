package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

public final class LengthOfNationalDestinationCode extends AbstractFunction implements UserDefinedFunction {

    protected LengthOfNationalDestinationCode() {
        super(2, PhoneNumberVocabulary.lengthOfNationalDestinationCode.stringValue());
    }

    private LengthOfNationalDestinationCode(final LengthOfNationalDestinationCode lengthOfNationalDestinationCode) {
        super(lengthOfNationalDestinationCode);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
      
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
