package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.text.WordUtils.*;

public final class Initials extends AbstractFunction implements StringFunction {

    protected Initials() {
        super(Range.closed(1, 2), StringVocabulary.initials.toString());
    }

    private Initials(final Initials initials) {
        super(initials);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(!assertStringLiteral(values[0])) {
            return ValueOrError.Error;
        }

        final String string = ((Literal)values[0]).label();

        switch (values.length) {
            case 1:
                return ValueOrError.General.of(literal(initials(string)));
            case 2: {
                if(!assertStringLiteral(values[1])) {
                    return ValueOrError.Error;
                }
                final String delimiters = ((Literal)values[1]).label();

                return ValueOrError.General.of(literal(initials(string, delimiters.toCharArray())));
            }
            default:
                return ValueOrError.Error;
        }
    }

    @Override
    public Initials copy() {
        return new Initials(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.initials.toString();
    }
}
