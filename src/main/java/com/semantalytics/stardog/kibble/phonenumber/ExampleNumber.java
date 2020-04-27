package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;
import static com.google.i18n.phonenumbers.Phonenumber.*;

public final class ExampleNumber extends AbstractFunction implements UserDefinedFunction {

    private final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    protected ExampleNumber() {
        super(1, PhoneNumberVocabulary.exampleNumber.stringValue());
    }

    private ExampleNumber(final ExampleNumber exampleNumber) {
        super(exampleNumber);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String regionCode = assertStringLiteral(values[0]).stringValue();

        return literal(phoneNumberUtil.getExampleNumber(regionCode).toString());
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
