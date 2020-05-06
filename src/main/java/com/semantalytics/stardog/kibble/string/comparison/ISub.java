package com.semantalytics.stardog.kibble.string.comparison;

import com.complexible.stardog.plan.filter.EvalUtil;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.Constant;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.ivml.alimo.I_Sub;

public final class ISub extends AbstractFunction implements StringFunction {

    private static final I_Sub iSub = new I_Sub();

    private ISub(ISub iSub) {
        super(iSub);
    }

    protected ISub() {
        super(Range.closed(2, 3), StringMetricVocabulary.isub.stringValue());
    }

    @Override
    public Function copy() {
        return new ISub(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertLiteral(values[0]) && assertLiteral(values[1])) {

            final String firstString = ((Literal) values[0]).label();
            final String secondString = ((Literal) values[1]).label();

            boolean normalizeStrings = false;

            if (values.length == 3 && (values[2] instanceof Constant && assertLiteral(values[2])) && EvalUtil.booleanValue(((Literal) values[2]).label()).isPresent()) {
                normalizeStrings = EvalUtil.booleanValue(((Literal) values[2]).label()).get();
                return ValueOrError.Double.of(iSub.score(firstString, secondString, normalizeStrings));
            } else {
                return ValueOrError.Double.of(iSub.score(firstString, secondString, false));
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.isub.name();
    }
}
