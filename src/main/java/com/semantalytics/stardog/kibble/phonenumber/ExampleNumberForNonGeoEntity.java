package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

public final class ExampleNumberForNonGeoEntity extends AbstractFunction implements UserDefinedFunction {

    protected ExampleNumberForNonGeoEntity() {
        super(2, PhoneNumberVocabulary.exampleNumberForNonGeoEntity.stringValue());
    }

    private ExampleNumberForNonGeoEntity(final ExampleNumberForNonGeoEntity exampleNumberForNonGeoEntity) {
        super(exampleNumberForNonGeoEntity);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
      
        return null;
    }

    @Override
    public Function copy() {
        return new ExampleNumberForNonGeoEntity(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.exampleNumberForNonGeoEntity.name();
    }
}
