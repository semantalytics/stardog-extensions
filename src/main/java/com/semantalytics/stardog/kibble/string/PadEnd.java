package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.google.common.base.Strings.*;
import static com.stardog.stark.Values.literal;

public final class PadEnd extends AbstractFunction implements StringFunction {

    protected PadEnd() {
        super(3, StringVocabulary.padEnd.toString());
    }

    private PadEnd(final PadEnd padEnd) {
        super(padEnd);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if (assertStringLiteral(values[0]) &&
            assertIntegerLiteral(values[1]) &&
            assertStringLiteral(values[2]) &&
            ((Literal)values[2]).label().length() == 1) {

            final String string = ((Literal) values[0]).label();
            final int minLength = Literal.intValue((Literal) values[1]);
            final char padChar = ((Literal) values[2]).label().charAt(0);

            return ValueOrError.General.of(literal(padEnd(string, minLength, padChar)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public PadEnd copy() {
        return new PadEnd(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.padEnd.name();
    }
}
