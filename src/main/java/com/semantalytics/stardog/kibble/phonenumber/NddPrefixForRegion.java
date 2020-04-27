package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

public final class NddPrefixForRegion extends AbstractFunction implements UserDefinedFunction {

    protected NddPrefixForRegion() {
        super(2, PhoneNumberVocabulary.nddPrefixForRegion.stringValue());
    }

    private NddPrefixForRegion(final NddPrefixForRegion nddPrefixForRegion) {
        super(nddPrefixForRegion);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
      
        return null;
    }

    @Override
    public Function copy() {
        return new NddPrefixForRegion(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.nddPrefixForRegion.name();
    }
}
