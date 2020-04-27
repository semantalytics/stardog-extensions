package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

public final class CanBeInternationallyDialed extends AbstractFunction implements UserDefinedFunction {

    protected CanBeInternationallyDialed() {
        super(2, PhoneNumberVocabulary.canBeInternationallyDialed.stringValue());
    }

    private CanBeInternationallyDialed(final CanBeInternationallyDialed canBeInternationallyDialed) {
        super(canBeInternationallyDialed);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
      
        return null;
    }

    @Override
    public Function copy() {
        return new CanBeInternationallyDialed(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.canBeInternationallyDialed.name();
    }
}
