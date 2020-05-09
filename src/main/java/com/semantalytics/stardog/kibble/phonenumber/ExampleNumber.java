package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.stardog.stark.Literal;

import static com.stardog.stark.Values.literal;

public final class ExampleNumber extends AbstractFunction implements UserDefinedFunction {

    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    protected ExampleNumber() {
        super(1, PhoneNumberVocabulary.exampleNumber.stringValue());
    }

    private ExampleNumber(final ExampleNumber exampleNumber) {
        super(exampleNumber);
    }

    @Override
    protected ValueOrError internalEvaluate(final com.stardog.stark.Value... values) {

        if(assertStringLiteral(values[0])) {
            final String regionCode = ((Literal)values[0]).label();
            return ValueOrError.General.of(literal(phoneNumberUtil.getExampleNumber(regionCode).toString()));
        } else {
            return ValueOrError.Error;
        }

    }

    @Override
    public Function copy() {
        return new ExampleNumber(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.exampleNumber.name();
    }
}
