package com.semantalytics.stardog.kibble.multimediafragments;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.github.tkurz.media.fragments.ParseException;
import com.github.tkurz.media.fragments.base.MediaFragment;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.github.tkurz.media.ontology.function.SpatialFunction;
import com.github.tkurz.media.ontology.type.SpatialEntity;
import com.google.common.collect.Range;
import com.semantalytics.stardog.kibble.multimediafragments.utils.Entities;
import com.semantalytics.stardog.kibble.multimediafragments.utils.SpatialEntities;
import com.stardog.stark.Value;

import java.util.List;
import java.util.Optional;

import static com.stardog.stark.Values.*;

public class SpatialBoundingBox extends AbstractFunction implements UserDefinedFunction {

    public SpatialBoundingBox() {
        super(Range.atLeast(2), Constants.NAMESPACE + "spatialBoundingBox");
    }

    public SpatialBoundingBox(final SpatialBoundingBox spatialBoundingBox) {
        super(spatialBoundingBox);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {
        if (Entities.haveComparable(values)) {
            try {

                final List<Optional<SpatialEntity>> entities = SpatialEntities.of(values);

                final String box = SpatialFunction.getBoundingBox(entities.stream().toArray(SpatialEntity[]::new)).stringValue();

                if (assertIRI(values[0])) {
                    final String uri = Entities.getBaseURI(values[0]);
                    return ValueOrError.General.of(iri(uri + MediaFragment.DEFAULT_TYPE.getDelimiter() + box));
                } else {
                    return ValueOrError.General.of(literal(box));
                }

            } catch (MediaFragmentURISyntaxException | ParseException e) {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public SpatialBoundingBox copy() {
        return new SpatialBoundingBox(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}
