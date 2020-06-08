package com.semantalytics.stardog.kibble.multimediafragments;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.github.tkurz.media.fragments.ParseException;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.github.tkurz.media.ontology.type.SpatialEntity;
import com.github.tkurz.media.ontology.type.SpatialTemporalEntity;
import com.google.common.collect.Range;
import com.semantalytics.stardog.kibble.multimediafragments.utils.SpatialTemporalEntities;
import com.stardog.stark.Value;

import java.util.Optional;

import static com.stardog.stark.Values.literal;

public class MediaFragment extends AbstractFunction implements UserDefinedFunction {

    public MediaFragment() {
        super(Range.closed(1, 2), Constants.NAMESPACE + "mediaFragment");
    }

    public MediaFragment(final MediaFragment mediaFragment) {
        super(mediaFragment);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {
        if(values.length == 1) {
            try {
                final Optional<SpatialTemporalEntity> entity = SpatialTemporalEntities.of(values[0]);
                if(entity.isPresent()) {
                    return ValueOrError.General.of(literal(entity.get().stringValue())); //TODO check
                } else {
                    return ValueOrError.Error;
                }
            } catch (MediaFragmentURISyntaxException | ParseException e) {
                return ValueOrError.Error;
            }
        } else if(values.length == 2) {
            //TODO implement specific to string parameters
            return ValueOrError.Error;
        } else {
            return ValueOrError.Error;
        }

    }

    @Override
    public MediaFragment copy() {
        return new MediaFragment(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return null;
    }
}
