package com.semantalytics.stardog.function.multimediafragments;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.github.tkurz.media.fragments.ParseException;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.github.tkurz.media.ontology.exception.NotComparableException;
import com.github.tkurz.media.ontology.function.TemporalFunction;
import com.github.tkurz.media.ontology.type.TemporalEntity;
import com.semantalytics.stardog.function.multimediafragments.utils.Entities;
import com.semantalytics.stardog.function.multimediafragments.utils.TemporalEntities;
import com.stardog.stark.Value;

import java.util.Optional;

import static com.stardog.stark.Values.literal;

public class TemporalContains extends AbstractFunction implements UserDefinedFunction {

    public TemporalContains() {
        super(2, Constants.NAMESPACE + "temporalContains");
    }

    public TemporalContains(final TemporalContains temporalContains) {
        super(temporalContains);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {
        if(Entities.haveComparable(values)) {
            try {
                final Optional<TemporalEntity> entity1 = TemporalEntities.of(values[0]);
                final Optional<TemporalEntity> entity2 = TemporalEntities.of(values[1]);

                if(entity1.isPresent() && entity2.isPresent()) {
                    return ValueOrError.General.of(literal(TemporalFunction.contains(entity1.get(), entity2.get())));
                } else {
                    return ValueOrError.Error;
                }
            } catch (ParseException | MediaFragmentURISyntaxException | NotComparableException e) {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public TemporalContains copy() {
        return new TemporalContains(this);
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}