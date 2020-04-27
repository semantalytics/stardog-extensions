package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

public final class MatchTypeNone extends AbstractFunction implements UserDefinedFunction {

    protected MatchTypeNone() {
        super(2, PhoneNumberVocabulary.matchTypeNone.stringValue());
    }

    private MatchTypeNone(final MatchTypeNone matchTypeNone) {
        super(matchTypeNone);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
      
        return null;
    }

    @Override
    public MatchTypeNone copy() {
        return new MatchTypeNone(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.matchTypeNone.name();
    }
}
