package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class IsAlphaSpace extends AbstractFunction implements StringFunction {

    protected IsAlphaSpace() {
        super(1, StringVocabulary.isAlphaSpace.toString());
    }

    private IsAlphaSpace(final IsAlphaSpace isUpperCase) {
        super(isUpperCase);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(!assertStringLiteral(values[0])) {
            return ValueOrError.Error;
        }

        final String string = ((Literal)values[0]).label();

        return ValueOrError.General.of(literal(isAlphaSpace(string)));
    }

    @Override
    public IsAlphaSpace copy() {
        return new IsAlphaSpace(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.isAlphaSpace.toString();
    }
}
