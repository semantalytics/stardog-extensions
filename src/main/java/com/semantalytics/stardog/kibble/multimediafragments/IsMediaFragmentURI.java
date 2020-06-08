package com.semantalytics.stardog.kibble.multimediafragments;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.semantalytics.stardog.kibble.multimediafragments.utils.MediaFragmentURIs;
import com.stardog.stark.Value;

public class IsMediaFragmentURI extends AbstractFunction implements UserDefinedFunction {

    public IsMediaFragmentURI() {
        super(1, Constants.NAMESPACE + "isMediaFragmentURI");
    }

    public IsMediaFragmentURI(final IsMediaFragmentURI isMediaFragmentURI) {
        super(isMediaFragmentURI);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {
        return ValueOrError.Boolean.of(MediaFragmentURIs.isMediaFragmentURI(values[0]));
    }

    @Override
    public IsMediaFragmentURI copy() {
        return new IsMediaFragmentURI(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}
