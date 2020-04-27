package com.semantalytics.stardog.kibble.string.emoji;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import emoji4j.EmojiUtils;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;
import static emoji4j.EmojiUtils.*;

public final class Emojify extends AbstractFunction implements StringFunction {

    protected Emojify() {
        super(1, EmojiVocabulary.emojify.stringValue());
    }

    private Emojify(final Emojify emojify) {
        super(emojify);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String text = assertStringLiteral(values[0]).stringValue();

        return literal(emojify(text));
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
        return EmojiVocabulary.emojify.name();
    }
}
