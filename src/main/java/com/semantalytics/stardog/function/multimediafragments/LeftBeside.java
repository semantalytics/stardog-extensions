package com.semantalytics.stardog.function.multimediafragments;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.github.tkurz.media.fragments.ParseException;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.github.tkurz.media.ontology.function.SpatialFunction;
import com.github.tkurz.media.ontology.type.SpatialEntity;
import com.google.common.collect.Range;
import com.semantalytics.stardog.function.multimediafragments.utils.Entities;
import com.semantalytics.stardog.function.multimediafragments.utils.SpatialEntities;
import com.stardog.stark.Value;

import java.util.Optional;

public class LeftBeside extends AbstractFunction implements UserDefinedFunction {

    public LeftBeside() {
        super(2, Constants.NAMESPACE + "leftBeside");
    }

    public LeftBeside(final LeftBeside leftBeside) {
        super(leftBeside);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {
        if(Entities.haveComparable(values)) {
            try {
                final Optional<SpatialEntity> entity1 = SpatialEntities.of(values[0]);
                final Optional<SpatialEntity> entity2 = SpatialEntities.of(values[1]);

                if(entity1.isPresent() && entity2.isPresent()) {

                    //TODO check if there is a Model, for now we take default model

                    return ValueOrError.Boolean.of(SpatialFunction.DirectionalFunction.leftBeside(entity1.get(), entity2.get()));
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
    public LeftBeside copy() {
        return new LeftBeside(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}