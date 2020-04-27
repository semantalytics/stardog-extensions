package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

public final class ExampleNumberForType extends AbstractFunction implements UserDefinedFunction {

    protected ExampleNumberForType() {
        super(2, PhoneNumberVocabulary.exampleNumberForType.stringValue());
    }

    private ExampleNumberForType(final ExampleNumberForType exampleNumberForType) {
        super(exampleNumberForType);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
      
        return null;
    }

    @Override
    public Function copy() {
        return new ExampleNumberForType(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.exampleNumberForType.name();
    }
}
