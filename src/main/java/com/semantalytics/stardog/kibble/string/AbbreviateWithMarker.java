package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.Values;

import static org.apache.commons.lang3.StringUtils.*;

public final class AbbreviateWithMarker extends AbstractFunction implements StringFunction {

    protected AbbreviateWithMarker() {
        super(Range.closed(3, 4), StringVocabulary.abbreviateWithMarker.toString());
    }

    private AbbreviateWithMarker(final AbbreviateWithMarker abbreviateWithMarker) {
        super(abbreviateWithMarker);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(!assertStringLiteral(values[0]) || !assertStringLiteral(values[1]) || !assertIntegerLiteral(values[2])) {
            return ValueOrError.Error;
        }

        final String string = ((Literal)values[0]).label();
        final String abbrevMarker = ((Literal)values[1]).label();
        final int maxWidth = Literal.intValue((Literal)values[2]);

        if (maxWidth <= 3) {
            return ValueOrError.Error;
        }

        switch (values.length) {
            case 3:
                return ValueOrError.General.of(Values.literal(abbreviate(string, abbrevMarker, maxWidth)));
            case 4:
                if(!assertIntegerLiteral(values[3])) {
                    return ValueOrError.Error;
                }
                final int offset = Literal.intValue((Literal)values[3]);
                return ValueOrError.General.of(Values.literal(abbreviate(string, abbrevMarker, offset, maxWidth)));
            default:
                return ValueOrError.Error;
        }
    }

    @Override
    public AbbreviateWithMarker copy() {
        return new AbbreviateWithMarker(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.abbreviateWithMarker.toString();
    }
}
