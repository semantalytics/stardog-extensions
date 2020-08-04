package com.semantalytics.stardog.kibble.string.emoji;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static emoji4j.EmojiUtils.*;

public final class Emojify extends AbstractFunction implements StringFunction {

    protected Emojify() {
        super(1, EmojiVocabulary.emojify.toString());
    }

    private Emojify(final Emojify emojify) {
        super(emojify);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String text = ((Literal)values[0]).label();

            return ValueOrError.General.of(literal(emojify(text)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Emojify copy() {
        return new Emojify(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return EmojiVocabulary.emojify.toString();
    }
}
