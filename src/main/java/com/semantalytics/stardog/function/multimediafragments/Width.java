package com.semantalytics.stardog.function.multimediafragments;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.github.tkurz.media.fragments.ParseException;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.github.tkurz.media.ontology.type.SpatialEntity;
import com.semantalytics.stardog.function.multimediafragments.utils.SpatialEntities;
import com.stardog.stark.Value;

import java.util.Optional;

public class Width extends AbstractFunction implements UserDefinedFunction {

    public Width() {
        super(1, Constants.NAMESPACE + "width");
    }

    public Width(final Width width) {
        super(width);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {
        if(assertLiteral(values[0]) || assertIRI(values[0])) {
            try {
                final Optional<SpatialEntity> entity = SpatialEntities.of(values[0]);

                if(entity.isPresent()) {
                    return ValueOrError.Double.of(entity.get().getBoundingBox().getWidth());
                } else {
                    return ValueOrError.Error;
                }
            } catch (ParseException | MediaFragmentURISyntaxException e) {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Function copy() {
        return new Width(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}
