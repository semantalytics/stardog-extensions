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

public final class DecimalHtml extends AbstractFunction implements StringFunction {

    protected DecimalHtml() {
        super(1, EmojiVocabulary.decimalHtml.toString());
    }

    private DecimalHtml(final DecimalHtml decimalHtml) {
        super(decimalHtml);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String string = ((Literal)values[0]).label();

            return ValueOrError.General.of(literal(Optional.ofNullable(getEmoji(string)).map(Emoji::getDecimalHtml).orElse("")));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public DecimalHtml copy() {
        return new DecimalHtml(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return EmojiVocabulary.decimalHtml.toString();
    }
}
