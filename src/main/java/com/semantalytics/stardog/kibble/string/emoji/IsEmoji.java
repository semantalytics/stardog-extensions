package com.semantalytics.stardog.kibble.string.emoji;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static emoji4j.EmojiUtils.*;

public final class IsEmoji extends AbstractFunction implements StringFunction {

    protected IsEmoji() {
        super(1, EmojiVocabulary.isEmoji.stringValue());
    }

    private IsEmoji(final IsEmoji isEmoji) {
        super(isEmoji);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String string = ((Literal)values[0]).label();

            return ValueOrError.General.of(literal(isEmoji(string)));
        } else {
            return ValueOrError.Error;
        }
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
