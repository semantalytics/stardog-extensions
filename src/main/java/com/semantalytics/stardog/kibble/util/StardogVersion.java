package com.semantalytics.stardog.kibble.util;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;

public class StardogVersion extends AbstractFunction implements UserDefinedFunction {

    protected StardogVersion() {
        super(0, UtilVocabulary.stardogVersion.stringValue());
    }

    public StardogVersion(final StardogVersion stardogVersion) {
        super(stardogVersion);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        return ValueOrError.General.of(literal(com.complexible.stardog.StardogVersion.VERSION));
    }

    @Override
    public StardogVersion copy() {
        return new StardogVersion(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return UtilVocabulary.stardogVersion.name();
    }
}
