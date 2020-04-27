package com.semantalytics.stardog.kibble.string.emoji;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import emoji4j.EmojiUtils;
import org.openrdf.model.Literal;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;
import static emoji4j.EmojiUtils.*;

public final class Htmlify extends AbstractFunction implements StringFunction {

    protected Htmlify() {
        super(Range.closed(1, 2), EmojiVocabulary.htmlify.stringValue());
    }

    private Htmlify(final Htmlify htmlify) {
        super(htmlify);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String string = assertStringLiteral(values[0]).stringValue();

        switch(values.length) {
            case 1: {
                return literal(htmlify(string));
            }
            case 2: {
                final boolean asSurrogate = assertLiteral(values[1]).booleanValue();
                return literal(htmlify(string, asSurrogate));

            }
            default: {
                throw new ExpressionEvaluationException("Function takes 2 or 3 arguments. Found " + values.length);
            }
        }

    }

    @Override
    public Htmlify copy() {
        return new Htmlify(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return EmojiVocabulary.htmlify.name();
    }
}
