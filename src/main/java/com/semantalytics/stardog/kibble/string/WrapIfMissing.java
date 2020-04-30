package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.apache.commons.lang3.StringUtils;

import static com.stardog.stark.Values.literal;

public final class WrapIfMissing extends AbstractFunction implements StringFunction {

    protected WrapIfMissing() {
        super(2, StringVocabulary.wrapIfMissing.toString());
    }

    private WrapIfMissing(final WrapIfMissing wrapIfMissing) {
        super(wrapIfMissing);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if (assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {

            final String string = ((Literal) values[0]).label();
            final String wrapWith = ((Literal) values[1]).label();

            if (wrapWith.length() != 1) {
                return ValueOrError.Error;
            }

            return ValueOrError.General.of(literal(StringUtils.wrapIfMissing(string, wrapWith.charAt(0))));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public WrapIfMissing copy() {
        return new WrapIfMissing(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.wrapIfMissing.name();
    }
}
