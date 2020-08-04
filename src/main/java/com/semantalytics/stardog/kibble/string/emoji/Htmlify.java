package com.semantalytics.stardog.kibble.string.emoji;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Datatype;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static emoji4j.EmojiUtils.*;

public final class Htmlify extends AbstractFunction implements StringFunction {

    protected Htmlify() {
        super(Range.closed(1, 2), EmojiVocabulary.htmlify.toString());
    }

    private Htmlify(final Htmlify htmlify) {
        super(htmlify);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String string = ((Literal)values[0]).label();

            switch (values.length) {
                case 1: {
                    return ValueOrError.General.of(literal(htmlify(string)));
                }
                case 2: {
                    if(assertTypedLiteral(values[1], Datatype.BOOLEAN)) {
                        final boolean asSurrogate = Literal.booleanValue((Literal)values[1]);
                        return ValueOrError.General.of(literal(htmlify(string, asSurrogate)));
                    } else {
                        return ValueOrError.Error;
                    }

                }
                default: {
                    return ValueOrError.Error;
                }
            }
        } else {
            return ValueOrError.Error;
        }

    }

    @Override
    public Htmlify copy() {
        return new Htmlify(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return EmojiVocabulary.htmlify.toString();
    }
}
