package com.semantalytics.stardog.function.multimediafragments;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.github.tkurz.media.fragments.ParseException;
import com.github.tkurz.media.fragments.base.MediaFragment;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.github.tkurz.media.ontology.exception.NotAggregatableException;
import com.github.tkurz.media.ontology.function.TemporalFunction;
import com.github.tkurz.media.ontology.type.TemporalEntity;
import com.semantalytics.stardog.function.multimediafragments.utils.Entities;
import com.semantalytics.stardog.function.multimediafragments.utils.TemporalEntities;
import com.stardog.stark.Value;

import java.util.Optional;

import static com.stardog.stark.Values.literal;

public class TemporalIntermediate extends AbstractFunction implements UserDefinedFunction {

    public TemporalIntermediate() {
        super(2, Constants.NAMESPACE + "temporalIntermediate");
    }

    public TemporalIntermediate(final TemporalIntermediate temporalIntermediate) {
        super(temporalIntermediate);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {
        if(Entities.haveComparable(values)) {
            try {
                final Optional<TemporalEntity> entity1 = TemporalEntities.of(values[0]);
                final Optional<TemporalEntity> entity2 = TemporalEntities.of(values[1]);

                if(entity1.isPresent() && entity2.isPresent()) {
                    final String box = TemporalFunction.getIntermediate(entity1.get(), entity2.get()).stringValue();

                    if (assertIRI(values[0])) {
                        final String uri = Entities.getBaseURI(values[0]);
                        return ValueOrError.General.of(literal(uri + MediaFragment.DEFAULT_TYPE.getDelimiter() + box));
                    } else {
                        return ValueOrError.General.of(literal(box));
                    }
                } else {
                    return ValueOrError.Error;
                }
            } catch (ParseException | MediaFragmentURISyntaxException | NotAggregatableException e) {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public TemporalIntermediate copy() {
        return new TemporalIntermediate(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}
