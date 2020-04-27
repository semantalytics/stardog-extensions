package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

public final class LeniencyExactGrouping extends AbstractFunction implements UserDefinedFunction {

    protected LeniencyExactGrouping() {
        super(2, PhoneNumberVocabulary.leniencyExactGrouping.stringValue());
    }

    private LeniencyExactGrouping(final LeniencyExactGrouping leniencyExactGrouping) {
        super(leniencyExactGrouping);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
      
        return null;
    }

    @Override
    public LeniencyExactGrouping copy() {
        return new LeniencyExactGrouping(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.leniencyExactGrouping.name();
    }
}
