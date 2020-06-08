package com.semantalytics.stardog.kibble.multimediafragments;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.github.tkurz.media.fragments.ParseException;
import com.github.tkurz.media.fragments.base.MediaFragment;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.github.tkurz.media.ontology.function.SpatialFunction;
import com.github.tkurz.media.ontology.function.TemporalFunction;
import com.github.tkurz.media.ontology.type.SpatialEntity;
import com.github.tkurz.media.ontology.type.TemporalEntity;
import com.google.common.collect.Range;
import com.semantalytics.stardog.kibble.multimediafragments.utils.Entities;
import com.google.common.base.Joiner;
import com.semantalytics.stardog.kibble.multimediafragments.utils.SpatialEntities;
import com.semantalytics.stardog.kibble.multimediafragments.utils.TemporalEntities;
import com.stardog.stark.Value;

import java.util.List;
import java.util.Optional;

import static com.stardog.stark.Values.literal;

public class BoundingBox extends AbstractFunction implements UserDefinedFunction {

    public BoundingBox() {
        super(Range.atLeast(2), Constants.NAMESPACE + "boundingBox");
    }

    public BoundingBox(final BoundingBox boundingBox) {
        super(boundingBox);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {

        if(Entities.haveComparable(values)) {

            final String[] res = new String[2];

            try {
                final List<Optional<SpatialEntity>> entities = SpatialEntities.of(values);
                res[0] = SpatialFunction.getBoundingBox(entities.stream().filter(Optional::isPresent).map(Optional::get).toArray(SpatialEntity[]::new)).stringValue();
            } catch (IllegalArgumentException | MediaFragmentURISyntaxException | ParseException e) {

            }

            try {
                final List<Optional<TemporalEntity>> entities = TemporalEntities.of(values);
                res[1] = TemporalFunction.getBoundingBox(entities.stream().filter(Optional::isPresent).map(Optional::get).toArray(TemporalEntity[]::new)).stringValue();
            } catch (IllegalArgumentException | MediaFragmentURISyntaxException | ParseException e) {
            }


            if (assertIRI(values[0])) {
                final String uri = Entities.getBaseURI(values[0]);
                return ValueOrError.General.of(literal(uri + MediaFragment.DEFAULT_TYPE.getDelimiter() + Joiner.on("&").skipNulls().join(res)));
            } else {
                return ValueOrError.General.of(literal(Joiner.on("&").skipNulls().join(res)));
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public BoundingBox copy() {
        return new BoundingBox(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}
