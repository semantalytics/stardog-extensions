package com.semantalytics.stardog.kibble.multimediafragments;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.github.tkurz.media.fragments.ParseException;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.github.tkurz.media.ontology.type.SpatialEntity;
import com.semantalytics.stardog.kibble.multimediafragments.utils.SpatialEntities;
import com.stardog.stark.Value;

import java.util.Optional;

import static com.stardog.stark.Values.literal;

public class Xy extends AbstractFunction implements UserDefinedFunction {

    public Xy() {
        super(1, Constants.NAMESPACE + "xy");
    }

    public Xy(final Xy xy) {
        super(xy);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {

        if(assertLiteral(values[0]) || assertIRI(values[0])) {
            try {
                final Optional<SpatialEntity> entity = SpatialEntities.of(values[0]);

                if(entity.isPresent()) {
                    return ValueOrError.General.of(literal(entity.get().getBoundingBox().getUpperLeft().stringValue()));
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
    public Xy copy() {
        return new Xy(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}