package com.semantalytics.stardog.kibble.string.emoji;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import emoji4j.Emoji;

import java.util.Optional;

import static com.stardog.stark.Values.literal;
import static emoji4j.EmojiUtils.*;
import static java.util.Collections.*;

public final class Emoticon extends AbstractFunction implements StringFunction {

    protected Emoticon() {
        super(1, EmojiVocabulary.emoticon.stringValue());
    }

    private Emoticon(final Emoticon emoticon) {
        super(emoticon);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String string = ((Literal)values[0]).label();

            //TODO arrayLiteral
            return ValueOrError.General.of(literal(String.join("\u001f", Optional.ofNullable(getEmoji(string)).map(Emoji::getEmoticons).orElse(emptyList()))));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Emoticon copy() {
        return new Emoticon(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return EmojiVocabulary.emoticon.name();
    }
}
