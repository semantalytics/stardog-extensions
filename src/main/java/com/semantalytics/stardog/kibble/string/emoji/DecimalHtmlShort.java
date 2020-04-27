package com.semantalytics.stardog.kibble.string.emoji;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import emoji4j.Emoji;
import emoji4j.EmojiUtils;
import org.openrdf.model.Value;

import java.util.Optional;

import static com.complexible.common.rdf.model.Values.literal;
import static emoji4j.EmojiUtils.*;

public final class DecimalHtmlShort extends AbstractFunction implements StringFunction {

    protected DecimalHtmlShort() {
        super(1, EmojiVocabulary.decimalHtmlShort.stringValue());
    }

    private DecimalHtmlShort(final DecimalHtmlShort decimalHtmlShort) {
        super(decimalHtmlShort);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String string = assertStringLiteral(values[0]).stringValue();

        return literal(Optional.ofNullable(getEmoji(string)).map(Emoji::getDecimalHtmlShort).orElse(""));
    }

    @Override
    public DecimalHtmlShort copy() {
        return new DecimalHtmlShort(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return EmojiVocabulary.decimalHtmlShort.name();
    }
}
