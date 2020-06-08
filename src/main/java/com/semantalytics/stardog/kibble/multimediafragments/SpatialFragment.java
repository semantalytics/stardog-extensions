package com.semantalytics.stardog.kibble.multimediafragments;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.github.tkurz.media.fragments.ParseException;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.github.tkurz.media.ontology.type.SpatialEntity;
import com.google.common.collect.Range;
import com.semantalytics.stardog.kibble.multimediafragments.utils.SpatialEntities;
import com.stardog.stark.Value;

import java.util.Optional;

import static com.stardog.stark.Values.literal;

public class SpatialFragment extends AbstractFunction implements UserDefinedFunction {

    public SpatialFragment() {
        super(Range.closed(1, 2), Constants.NAMESPACE + "spatialFragment");
    }

    public SpatialFragment(final SpatialFragment spatialFragment) {
        super(spatialFragment);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {

        if(assertLiteral(values[0]) || assertIRI(values[0])) {
            if (values.length == 1) {
                try {
                    final Optional<SpatialEntity> entity = SpatialEntities.of(values[0]);
                    if(entity.isPresent()) {
                        return ValueOrError.General.of(literal(entity.get().stringValue())); //TODO check
                    } else {
                        return ValueOrError.Error;
                    }
                } catch (MediaFragmentURISyntaxException | ParseException e) {
                    return ValueOrError.Error;
                }
            } else if (values.length == 2) {
                //TODO implement specific to string parameters
            }

            return null;
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public SpatialFragment copy() {
        return new SpatialFragment(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}
