package com.semantalytics.stardog.function.multimediafragments;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.github.tkurz.media.fragments.ParseException;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.github.tkurz.media.ontology.type.TemporalEntity;
import com.semantalytics.stardog.function.multimediafragments.utils.Entities;
import com.semantalytics.stardog.function.multimediafragments.utils.TemporalEntities;
import com.stardog.stark.Value;

import java.util.Optional;

public class Duration extends AbstractFunction implements UserDefinedFunction {

    public Duration() {
        super(1, Constants.NAMESPACE + "duration");
    }

    public Duration(final Duration duration) {
        super(duration);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {
        try {
            final Optional<TemporalEntity> entity = TemporalEntities.of(values[0]);

            if (entity.isPresent()) {
                return ValueOrError.Double.of(entity.get().getEnd().getValue() - entity.get().getStart().getValue());
            } else {
                return ValueOrError.Error;
            }
        } catch (ParseException | MediaFragmentURISyntaxException e) {
            return ValueOrError.Error;
        }
    }

    @Override
    public Duration copy() {
        return new Duration();
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}
