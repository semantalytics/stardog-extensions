package com.semantalytics.stardog.kibble.util;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;

public class StardogVersion extends AbstractFunction implements UserDefinedFunction {

    protected StardogVersion() {
        super(0, UtilVocabulary.stardogVersion.stringValue());
    }

    public StardogVersion(final StardogVersion stardogVersion) {
        super(stardogVersion);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
        return literal(com.complexible.stardog.StardogVersion.VERSION);
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
