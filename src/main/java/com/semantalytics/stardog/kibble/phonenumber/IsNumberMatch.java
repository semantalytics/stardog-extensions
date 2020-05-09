package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;

public final class IsNumberMatch extends AbstractFunction implements UserDefinedFunction {

    protected IsNumberMatch() {
        super(2, PhoneNumberVocabulary.isNumberMatch.stringValue());
    }

    private IsNumberMatch(final IsNumberMatch isNumberMatch) {
        super(isNumberMatch);
    }

    @Override
    protected ValueOrError internalEvaluate(final com.stardog.stark.Value... values) {

        return null;
    }

    @Override
    public IsNumberMatch copy() {
        return new IsNumberMatch(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.isNumberMatch.name();
    }
}
