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
import static java.lang.String.join;
import static java.util.Collections.emptyList;

public final class Aliases extends AbstractFunction implements StringFunction {

    protected Aliases() {
        super(1, EmojiVocabulary.aliases.stringValue());
    }

    private Aliases(final Aliases aliases) {
        super(aliases);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        if(assertStringLiteral(values[0])) {
            final String string = ((Literal)values[0]).label();

            //TODO return list array

            return ValueOrError.General.of(literal(join("\u001f", Optional.ofNullable(getEmoji(string)).map(Emoji::getAliases).orElse(emptyList()))));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Aliases copy() {
        return new Aliases(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return EmojiVocabulary.aliases.name();
    }
}
