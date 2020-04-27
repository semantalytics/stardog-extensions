package com.semantalytics.stardog.kibble.string.emoji;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import emoji4j.EmojiUtils;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;
import static emoji4j.EmojiUtils.*;

public final class IsEmoji extends AbstractFunction implements StringFunction {

    protected IsEmoji() {
        super(1, EmojiVocabulary.isEmoji.stringValue());
    }

    private IsEmoji(final IsEmoji isEmoji) {
        super(isEmoji);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String string = assertStringLiteral(values[0]).stringValue();

        return literal(isEmoji(string));
    }

    @Override
    public IsEmoji copy() {
        return new IsEmoji(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return EmojiVocabulary.isEmoji.name();
    }
}
