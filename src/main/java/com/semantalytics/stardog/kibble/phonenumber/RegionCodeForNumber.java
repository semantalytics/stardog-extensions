package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

public final class RegionCodeForNumber extends AbstractFunction implements UserDefinedFunction {

    protected RegionCodeForNumber() {
        super(2, PhoneNumberVocabulary.regionCodeForNumber.stringValue());
    }

    private RegionCodeForNumber(final RegionCodeForNumber regionCodeForNumber) {
        super(regionCodeForNumber);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
      
        return null;
    }

    @Override
    public Function copy() {
        return new RegionCodeForNumber(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.regionCodeForNumber.name();
    }
}
