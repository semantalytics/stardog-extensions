package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.apache.commons.lang3.StringUtils;

import static com.stardog.stark.Values.literal;

public final class Overlay extends AbstractFunction implements StringFunction {

    protected Overlay() {
        super(4, StringVocabulary.overlay.toString());
    }

    private Overlay(final Overlay overlay) {
        super(overlay);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if (assertStringLiteral(values[0]) && assertStringLiteral(values[1]) && assertIntegerLiteral(values[2]) && assertIntegerLiteral(values[3])) {

            final String string = ((Literal) values[0]).label();
            final String overlay = ((Literal) values[1]).label();
            final int start = Literal.intValue((Literal) values[2]);
            final int end = Literal.intValue((Literal) values[3]);

            return ValueOrError.General.of(literal(StringUtils.overlay(string, overlay, start, end)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Overlay copy() {
        return new Overlay(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.overlay.name();
    }
}
