package com.semantalytics.stardog.kibble.string.emoji;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import emoji4j.Emoji;
import org.openrdf.model.Value;

import java.util.Optional;

import static com.complexible.common.rdf.model.Values.literal;
import static emoji4j.EmojiUtils.getEmoji;

public final class Unicode extends AbstractFunction implements StringFunction {

    protected Unicode() {
        super(1, EmojiVocabulary.unicode.stringValue());
    }

    private Unicode(final Unicode unicode) {
        super(unicode);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String string = assertStringLiteral(values[0]).stringValue();

        return literal(Optional.ofNullable(getEmoji(string)).map(Emoji::getEmoji).orElse(""));
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
