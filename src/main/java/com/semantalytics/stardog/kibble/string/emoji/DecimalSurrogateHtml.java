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

public final class DecimalSurrogateHtml extends AbstractFunction implements StringFunction {

    protected DecimalSurrogateHtml() {
        super(1, EmojiVocabulary.decimalSurrogateHtml.toString());
    }

    private DecimalSurrogateHtml(final DecimalSurrogateHtml decimalSurrogateHtml) {
        super(decimalSurrogateHtml);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String string = ((Literal)values[0]).label();

            return ValueOrError.General.of(literal(Optional.ofNullable(getEmoji(string)).map(Emoji::getDecimalSurrogateHtml).orElse("")));
        } else {
            return ValueOrError.Error;
        }
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
        return EmojiVocabulary.decimalSurrogateHtml.toString();
    }
}
