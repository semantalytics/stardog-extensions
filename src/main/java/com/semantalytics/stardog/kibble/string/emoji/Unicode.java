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
import static emoji4j.EmojiUtils.getEmoji;

public final class Unicode extends AbstractFunction implements StringFunction {

    protected Unicode() {
        super(1, EmojiVocabulary.unicode.stringValue());
    }

    private Unicode(final Unicode unicode) {
        super(unicode);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String string = ((Literal)values[0]).label();

            return ValueOrError.General.of(literal(Optional.ofNullable(getEmoji(string)).map(Emoji::getEmoji).orElse("")));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Unicode copy() {
        return new Unicode(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return EmojiVocabulary.unicode.name();
    }
}
