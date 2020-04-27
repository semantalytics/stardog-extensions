package com.semantalytics.stardog.kibble.util;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Value;

public class Pluralize extends AbstractFunction implements UserDefinedFunction {

    protected Pluralize() {
        super(1, UtilVocabulary.pluralize.stringValue());
    }

    public Pluralize(final Pluralize pluralize) {
        super(pluralize);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        return null;
    }

    @Override
    public Function copy() {
        return new Pluralize(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return UtilVocabulary.pluralize.name();
    }
}
