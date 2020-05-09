package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;

public final class InvalidExampleNumber extends AbstractFunction implements UserDefinedFunction {

    protected InvalidExampleNumber() {
        super(2, PhoneNumberVocabulary.invalidExampleNumber.stringValue());
    }

    private InvalidExampleNumber(final InvalidExampleNumber invalidExampleNumber) {
        super(invalidExampleNumber);
    }

    @Override
    protected ValueOrError internalEvaluate(final com.stardog.stark.Value... values) {

        return null;
    }

    @Override
    public InvalidExampleNumber copy() {
        return new InvalidExampleNumber(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.invalidExampleNumber.name();
    }
}
