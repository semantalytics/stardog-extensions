package com.semantalytics.stardog.kibble.string.emoji;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static emoji4j.EmojiUtils.*;

public final class ShortCodify extends AbstractFunction implements StringFunction {

    protected ShortCodify() {
        super(1, EmojiVocabulary.shortCodify.toString());
    }

    private ShortCodify(final ShortCodify shortCodify) {
        super(shortCodify);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String string = ((Literal)values[0]).label();

            return ValueOrError.General.of(literal(shortCodify(string)));
        } else {
            return ValueOrError.Error;
        }
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
        return EmojiVocabulary.shortCodify.toString();
    }
}
