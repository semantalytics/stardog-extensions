package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class StripEnd extends AbstractFunction implements StringFunction {

    protected StripEnd() {
        super(2, StringVocabulary.stripEnd.toString());
    }

    private StripEnd(final StripEnd stripEnd) {
        super(stripEnd);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(!assertStringLiteral(values[0]) || !assertStringLiteral(values[1])) {
            return ValueOrError.Error;
        }

        final String string = ((Literal)values[0]).label();
        final String stripChars = ((Literal)values[1]).label();

        return ValueOrError.General.of(literal(stripEnd(string, stripChars)));
    }

    @Override
    public StripEnd copy() {
        return new StripEnd(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.stripEnd.name();
    }
}
