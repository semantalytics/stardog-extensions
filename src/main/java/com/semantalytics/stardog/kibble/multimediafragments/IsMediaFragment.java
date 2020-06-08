package com.semantalytics.stardog.kibble.multimediafragments;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.semantalytics.stardog.kibble.multimediafragments.utils.MediaFragments;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

public class IsMediaFragment extends AbstractFunction implements UserDefinedFunction {

    public IsMediaFragment() {
        super(1, Constants.NAMESPACE + "IsMediaFragment");
    }

    public IsMediaFragment(final IsMediaFragment isMediaFragment) {
        super(isMediaFragment);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {
        if(assertLiteral(values[0])) {
            return ValueOrError.Boolean.of(MediaFragments.isMediaFragment((Literal)values[0]));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public IsMediaFragment copy() {
        return new IsMediaFragment(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}