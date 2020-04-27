package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

public final class IsPossibleNumberWithReason extends AbstractFunction implements UserDefinedFunction {

    protected IsPossibleNumberWithReason() {
        super(2, PhoneNumberVocabulary.isPossibleNumberWithReason.stringValue());
    }

    private IsPossibleNumberWithReason(final IsPossibleNumberWithReason isPossibleNumberWithReason) {
        super(isPossibleNumberWithReason);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
      
        return null;
    }

    @Override
    public IsPossibleNumberWithReason copy() {
        return new IsPossibleNumberWithReason(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.isPossibleNumberWithReason.name();
    }
}
