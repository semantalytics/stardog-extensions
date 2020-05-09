package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;

public final class TypeVoiceMail extends AbstractFunction implements UserDefinedFunction {

    protected TypeVoiceMail() {
        super(2, PhoneNumberVocabulary.typeVoiceMail.stringValue());
    }

    private TypeVoiceMail(final TypeVoiceMail typeVoiceMail) {
        super(typeVoiceMail);
    }

    @Override
    protected ValueOrError internalEvaluate(final com.stardog.stark.Value... values) {

        return null;
    }

    @Override
    public Function copy() {
        return new TypeVoiceMail(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.typeVoiceMail.name();
    }
}
