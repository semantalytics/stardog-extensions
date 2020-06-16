package com.semantalytics.stardog.kibble.geo;

import com.complexible.stardog.plan.filter.AbstractExpression;
import com.complexible.stardog.plan.filter.EvalUtil;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.ValueSolution;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Lists;

import java.util.List;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertLiteral;

public class ToDegreesMinutesSeconds extends AbstractExpression implements UserDefinedFunction {

    @Override
    public String getName() {
        return GeoVocabulary.toHoursMinutesSeconds.stringValue();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public ToDegreesMinutesSeconds copy() {
        return new ToDegreesMinutesSeconds(this);
    }

    @Override
    public void accept(final ExpressionVisitor theVisitor) {
        theVisitor.visit(this);
    }

    @Override
    public ValueOrError evaluate(ValueSolution theValueSolution) {
        ValueOrError firstArgValueOrError = getFirstArg().evaluate(theValueSolution);
        if(!firstArgValueOrError.isError() && assertLiteral(firstArgValueOrError.value()) && EvalUtil.isNumericDatatype(DataType.))
    }

}
