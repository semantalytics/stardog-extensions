package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;

public final class MatchTypeNone extends AbstractFunction implements UserDefinedFunction {

    protected MatchTypeNone() {
        super(2, PhoneNumberVocabulary.matchTypeNone.stringValue());
    }

    private MatchTypeNone(final MatchTypeNone matchTypeNone) {
        super(matchTypeNone);
    }

    @Override
    protected ValueOrError internalEvaluate(final com.stardog.stark.Value... values) {

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
