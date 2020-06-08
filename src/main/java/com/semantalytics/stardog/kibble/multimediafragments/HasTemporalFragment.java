package com.semantalytics.stardog.kibble.multimediafragments;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.github.tkurz.media.fragments.ParseException;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.semantalytics.stardog.kibble.multimediafragments.utils.TemporalEntities;
import com.stardog.stark.Value;

public class HasTemporalFragment extends AbstractFunction implements UserDefinedFunction {

    public HasTemporalFragment() {
        super(1, Constants.NAMESPACE + "hasTemporalFragment");
    }

    public HasTemporalFragment(final HasTemporalFragment hasTemporalFragment) {
        super(hasTemporalFragment);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {
        try {
            return ValueOrError.Boolean.of(TemporalEntities.of(values[0]).isPresent());
        } catch (ParseException | MediaFragmentURISyntaxException e) {
            return ValueOrError.Error;
        }
    }

    @Override
    public HasTemporalFragment copy() {
        return new HasTemporalFragment(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}
