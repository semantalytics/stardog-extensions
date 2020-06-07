package com.semantalytics.stardog.function.multimediafragments;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.github.tkurz.media.fragments.ParseException;
import com.github.tkurz.media.fragments.base.MediaFragment;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.github.tkurz.media.ontology.function.SpatialFunction;
import com.github.tkurz.media.ontology.type.SpatialEntity;
import com.semantalytics.stardog.function.multimediafragments.utils.Entities;
import com.semantalytics.stardog.function.multimediafragments.utils.SpatialEntities;
import com.stardog.stark.Value;

import java.util.Optional;

import static com.stardog.stark.Values.iri;
import static com.stardog.stark.Values.literal;

public class SpatialIntersection extends AbstractFunction implements UserDefinedFunction {

    public SpatialIntersection() {
        super(2, Constants.NAMESPACE + "spatialIntersection");
    }

    public SpatialIntersection(final SpatialIntersection spatialIntersection) {
        super(spatialIntersection);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {
        if(Entities.haveComparable(values)) {
            try {
                final Optional<SpatialEntity> entity1 = SpatialEntities.of(values[0]);
                final Optional<SpatialEntity> entity2 = SpatialEntities.of(values[1]);

                if(entity1.isPresent() && entity2.isPresent()) {

                    final String box = SpatialFunction.getIntersect(entity1.get(), entity2.get()).stringValue();

                    if (assertIRI(values[0])) {
                        String uri = Entities.getBaseURI(values[0]);
                        return ValueOrError.General.of(iri(uri + MediaFragment.DEFAULT_TYPE.getDelimiter() + box));
                    } else {
                        return ValueOrError.General.of(literal(box));
                    }
                } else {
                    return ValueOrError.Error;
                }
            } catch(ParseException | MediaFragmentURISyntaxException e) {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public SpatialIntersection copy() {
        return new SpatialIntersection(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}
