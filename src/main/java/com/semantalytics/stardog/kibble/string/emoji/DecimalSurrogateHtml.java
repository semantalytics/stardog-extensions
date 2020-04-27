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

public final class DecimalSurrogateHtml extends AbstractFunction implements StringFunction {

    protected DecimalSurrogateHtml() {
        super(1, EmojiVocabulary.decimalSurrogateHtml.stringValue());
    }

    private DecimalSurrogateHtml(final DecimalSurrogateHtml decimalSurrogateHtml) {
        super(decimalSurrogateHtml);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String string = assertStringLiteral(values[0]).stringValue();

        return literal(Optional.ofNullable(getEmoji(string)).map(Emoji::getDecimalSurrogateHtml).orElse(""));
    }

    @Override
    public DecimalSurrogateHtml copy() {
        return new DecimalSurrogateHtml(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return EmojiVocabulary.decimalSurrogateHtml.name();
    }
}
