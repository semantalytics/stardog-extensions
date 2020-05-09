package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;

public final class TypeFixedLineOrMobile extends AbstractFunction implements UserDefinedFunction {

    protected TypeFixedLineOrMobile() {
        super(2, PhoneNumberVocabulary.typeFixedLineOrMobile.stringValue());
    }

    private TypeFixedLineOrMobile(final TypeFixedLineOrMobile typeFixedLineOrMobile) {
        super(typeFixedLineOrMobile);
    }

    @Override
    protected ValueOrError internalEvaluate(final com.stardog.stark.Value... values) {

        return null;
    }

    @Override
    public Function copy() {
        return new TypeFixedLineOrMobile(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.typeFixedLineOrMobile.name();
    }
}
