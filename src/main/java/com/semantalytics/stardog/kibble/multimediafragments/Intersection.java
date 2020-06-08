package com.semantalytics.stardog.kibble.multimediafragments;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.github.tkurz.media.fragments.base.MediaFragment;
import com.github.tkurz.media.ontology.exception.NotAggregatableException;
import com.github.tkurz.media.ontology.function.SpatialFunction;
import com.github.tkurz.media.ontology.function.TemporalFunction;
import com.github.tkurz.media.ontology.type.SpatialEntity;
import com.github.tkurz.media.ontology.type.TemporalEntity;
import com.google.common.collect.Range;
import com.semantalytics.stardog.kibble.multimediafragments.utils.Entities;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;

public class Intersection extends AbstractFunction implements UserDefinedFunction {

    public Intersection() {
        super(Range.atLeast(2), Constants.NAMESPACE + "intersection");
    }

    public Intersection(final Intersection intersection) {
        super(intersection);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {

        if (Entities.haveComparable(values)) {

            final String[] res = new String[2];

            /*
            try {
                final List<Optional<SpatialEntity>> spatialEntities = SpatialEntities.of(values);
                res[0] = SpatialFunction.getIntersect(spatialEntities.stream().toArray(Optional[]::new)).stringValue();
            } catch (IllegalArgumentException e) {
            }

            try {
                List<Optional<TemporalEntity>> temporalEntities = TemporalEntities.of(values);
                Arrays.stream(entities).
                res[1] = TemporalFunction.getIntersect(entities[0], entities[1]).stringValue();
            } catch (IllegalArgumentException | NotAggregatableException e) {
            }

            if (assertIRI(values[0])) {
                final String uri = Entities.getBaseURI(values[0]);
                return ValueOrError.General.of(literal(uri + MediaFragment.DEFAULT_TYPE.getDelimiter() + Joiner.on("&").skipNulls().join(res)));
            } else {
                return ValueOrError.General.of(literal(Joiner.on("&").skipNulls().join(res)));
            }

             */
            return null;
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Intersection copy() {
        return new Intersection(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}
