package com.semantalytics.stardog.function.multimediafragments;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.semantalytics.stardog.function.multimediafragments.utils.MediaFragmentURIs;
import com.semantalytics.stardog.function.multimediafragments.utils.MediaFragments;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

public class isMediaFragment extends AbstractFunction implements UserDefinedFunction {

    public isMediaFragment() {
        super(1, Constants.NAMESPACE + "isMediaFragment");
    }

    public isMediaFragment(final isMediaFragment isMediaFragment) {
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
    public isMediaFragment copy() {
        return new isMediaFragment(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}