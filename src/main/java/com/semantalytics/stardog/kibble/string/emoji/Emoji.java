package com.semantalytics.stardog.kibble.string.emoji;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import emoji4j.EmojiUtils;

import static com.stardog.stark.Values.literal;
import static emoji4j.EmojiUtils.emojify;
import static emoji4j.EmojiUtils.getEmoji;

public final class Emoji extends AbstractFunction implements StringFunction {

    protected Emoji() {
        super(1, EmojiVocabulary.emoji.toString());
    }

    private Emoji(final Emoji emoji) {
        super(emoji);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String text = ((Literal)values[0]).label();

            return ValueOrError.General.of(literal(EmojiUtils.getEmoji(text).getEmoji()));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Emoji copy() {
        return new Emoji(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return EmojiVocabulary.emoji.toString();
    }
}
