package com.semantalytics.stardog.kibble.multimediafragments;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.github.tkurz.media.fragments.ParseException;
import com.github.tkurz.media.fragments.base.MediaFragment;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.github.tkurz.media.ontology.function.TemporalFunction;
import com.github.tkurz.media.ontology.type.TemporalEntity;
import com.google.common.collect.Range;
import com.semantalytics.stardog.kibble.multimediafragments.utils.Entities;
import com.semantalytics.stardog.kibble.multimediafragments.utils.TemporalEntities;
import com.stardog.stark.Value;

import java.util.List;
import java.util.Optional;

import static com.stardog.stark.Values.literal;

public class TemportalBoundingBox extends AbstractFunction implements UserDefinedFunction {

    public TemportalBoundingBox() {
        super(Range.atLeast(2), Constants.NAMESPACE + "temporalBoundingBox");
    }

    public TemportalBoundingBox(final TemportalBoundingBox temportalBoundingBox) {
        super(temportalBoundingBox);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {
        if(Entities.haveComparable(values)) {
            try {
                final List<Optional<TemporalEntity>> entities = TemporalEntities.of(values);

                if(entities.stream().allMatch(Optional::isPresent)) {
                    final String box = TemporalFunction.getBoundingBox(entities.stream().map(Optional::get).toArray(TemporalEntity[]::new)).stringValue();

                    if (assertIRI(values[0])) {
                        String uri = Entities.getBaseURI(values[0]);
                        //TODO What type should this be anyURI or string?????
                        return ValueOrError.General.of(literal(uri + MediaFragment.DEFAULT_TYPE.getDelimiter() + box));
                    } else {
                        return ValueOrError.General.of(literal(box));
                    }
                } else {
                    return ValueOrError.Error;
                }
            } catch (ParseException | MediaFragmentURISyntaxException e)  {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public TemportalBoundingBox copy() {
        return new TemportalBoundingBox(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}
