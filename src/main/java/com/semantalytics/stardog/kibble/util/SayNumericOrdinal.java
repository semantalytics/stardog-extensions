package com.semantalytics.stardog.kibble.util;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Value;

public class SayNumericOrdinal extends AbstractFunction implements UserDefinedFunction {

    protected SayNumericOrdinal() {
        super(1, UtilVocabulary.sayNumericOrdinal.stringValue());
    }

    public SayNumericOrdinal(final SayNumericOrdinal sayNumericOrdinal) {
        super(sayNumericOrdinal);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        return null;
    }

    @Override
    public Function copy() {
        return new SayNumericOrdinal(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return UtilVocabulary.sayNumericOrdinal.name();
    }
}
