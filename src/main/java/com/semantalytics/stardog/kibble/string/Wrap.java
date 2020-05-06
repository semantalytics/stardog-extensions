package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class Wrap extends AbstractFunction implements StringFunction {

    protected Wrap() {
        super(2, StringVocabulary.wrap.toString());
    }

    private Wrap(final Wrap wrap) {
        super(wrap);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        final ValueOrError result;

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {

            final String string = ((Literal) values[0]).label();
            final String wrapWith = ((Literal) values[1]).label();

            if (wrapWith.length() == 1) {
                result = ValueOrError.General.of(literal(wrap(string, wrapWith.charAt(0))));
            } else {
                result = ValueOrError.Error;
            }
        } else {
            result = ValueOrError.Error;
        }
        return result;
    }

    @Override
    public Wrap copy() {
        return new Wrap(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.wrap.name();
    }
}
