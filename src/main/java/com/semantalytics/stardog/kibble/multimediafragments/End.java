package com.semantalytics.stardog.kibble.multimediafragments;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.github.tkurz.media.fragments.ParseException;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.github.tkurz.media.ontology.type.TemporalEntity;
import com.semantalytics.stardog.kibble.multimediafragments.utils.TemporalEntities;
import com.stardog.stark.Value;

import java.util.Optional;

public class End extends AbstractFunction implements UserDefinedFunction {

    public End() {
        super(1, Constants.NAMESPACE + "end");
    }

    public End(final End end) {
        super(end);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {
        try {
            final Optional<TemporalEntity> entity = TemporalEntities.of(values[0]);

            if(entity.isPresent()) {
                return ValueOrError.Double.of(entity.get().getEnd().getValue());
            } else {
                return ValueOrError.Error;
            }
        } catch (ParseException | MediaFragmentURISyntaxException e) {
            return ValueOrError.Error;
        }
    }

    @Override
    public End copy() {
        return new End(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}