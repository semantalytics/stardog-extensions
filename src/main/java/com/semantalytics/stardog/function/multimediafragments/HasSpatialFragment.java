package com.semantalytics.stardog.function.multimediafragments;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.github.tkurz.media.fragments.ParseException;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.semantalytics.stardog.function.multimediafragments.utils.SpatialEntities;
import com.stardog.stark.Value;

public class HasSpatialFragment extends AbstractFunction implements UserDefinedFunction {

    public HasSpatialFragment() {
        super(1, Constants.NAMESPACE + "hasSpatialFragment");
    }

    public HasSpatialFragment(final HasSpatialFragment spatialFragment) {
        super(spatialFragment);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {
        try {
            return ValueOrError.Boolean.of(SpatialEntities.of(values[0]).isPresent());
        } catch (ParseException | MediaFragmentURISyntaxException e) {
            return ValueOrError.Boolean.False;
        }
    }

    @Override
    public HasSpatialFragment copy() {
        return new HasSpatialFragment(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}
