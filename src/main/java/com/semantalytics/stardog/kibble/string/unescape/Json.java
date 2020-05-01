package com.semantalytics.stardog.kibble.string.unescape;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.text.StringEscapeUtils.unescapeJson;

public final class Json extends AbstractFunction implements StringFunction {

    protected Json() {
        super(1, UnescapeVocabulary.json.stringValue());
    }

    private Json(final Json json) {
        super(json);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String string = ((Literal)values[0]).label();

            return ValueOrError.General.of(literal(unescapeJson(string)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Json copy() {
        return new Json(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return UnescapeVocabulary.csv.name();
    }
}
