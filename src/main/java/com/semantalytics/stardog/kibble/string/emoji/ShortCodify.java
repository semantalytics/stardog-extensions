package com.semantalytics.stardog.kibble.string.emoji;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import emoji4j.EmojiUtils;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;
import static emoji4j.EmojiUtils.*;

public final class ShortCodify extends AbstractFunction implements StringFunction {

    protected ShortCodify() {
        super(1, EmojiVocabulary.shortCodify.stringValue());
    }

    private ShortCodify(final ShortCodify shortCodify) {
        super(shortCodify);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String string = assertStringLiteral(values[0]).stringValue();

        return literal(shortCodify(string));
    }

    @Override
    public ShortCodify copy() {
        return new ShortCodify(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return EmojiVocabulary.shortCodify.name();
    }
}
