package com.semantalytics.stardog.kibble.multimediafragments;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.github.tkurz.media.fragments.ParseException;
import com.github.tkurz.media.fragments.exceptions.MediaFragmentURISyntaxException;
import com.github.tkurz.media.ontology.type.TemporalEntity;
import com.google.common.collect.Range;
import com.semantalytics.stardog.kibble.multimediafragments.utils.TemporalEntities;
import com.stardog.stark.Value;

import java.util.Optional;

import static com.stardog.stark.Values.literal;

public class TemporalFragment extends AbstractFunction implements UserDefinedFunction {

    public TemporalFragment() {
        super(Range.closed(2, 3), Constants.NAMESPACE + "temporalFragment");
    }

    public TemporalFragment(final TemporalFragment temporalFragment) {
        super(temporalFragment);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {
        if (values.length == 1) {
            try {
                Optional<TemporalEntity> entity = TemporalEntities.of(values[0]);
                if (entity.isPresent()) {
                    return ValueOrError.General.of(literal(entity.get().stringValue())); //TODO check
                } else {
                    return ValueOrError.Error;
                }
            } catch (ParseException | MediaFragmentURISyntaxException e) {
                return ValueOrError.Error;
            }
        } else if (values.length == 2) {
            //TODO implement specific to string parameters
            return ValueOrError.Error;
        } else {
            return ValueOrError.Error;
        }

    }

    @Override
    public TemporalFragment copy() {
        return new TemporalFragment(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}
