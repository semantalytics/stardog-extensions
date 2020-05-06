package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class Abbreviate extends AbstractFunction implements StringFunction {

    protected Abbreviate() {
        super(Range.closed(2, 3), StringVocabulary.abbreviate.toString());
    }

    private Abbreviate(final Abbreviate abbreviate) {
        super(abbreviate);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if (!assertStringLiteral(values[0]) || !assertIntegerLiteral(values[1])) {
            return ValueOrError.Error;
        }

        final String string = ((Literal) values[0]).label();
        final int maxWidth = Literal.intValue((Literal) values[1]);

        if (maxWidth <= 3) {
            return ValueOrError.Error;
        }

        switch (values.length) {
            case 2:
                return ValueOrError.General.of(literal(abbreviate(string, maxWidth)));
            case 3: {
                if (!assertIntegerLiteral(values[2])) {
                    return ValueOrError.Error;
                }
                final int offset = Literal.intValue((Literal) values[2]);
                return ValueOrError.General.of(literal(abbreviate(string, offset, maxWidth)));
            }
            default:
                return ValueOrError.Error;
        }
    }

    @Override
    public Abbreviate copy() {
        return new Abbreviate(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.abbreviate.name();
    }
}
