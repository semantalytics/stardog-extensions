package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class StartsWithIgnoreCase extends AbstractFunction implements StringFunction {

    protected StartsWithIgnoreCase() {
        super(2, StringVocabulary.startsWithIgnoreCase.toString());
    }

    private StartsWithIgnoreCase(final StartsWithIgnoreCase startsWithIgnoreCase) {
        super(startsWithIgnoreCase);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        for(final Value value : values) {
            if(!assertStringLiteral(value)) {
                return ValueOrError.Error;
            }
        }

        final String string = ((Literal)values[0]).label();
        final String prefix = ((Literal)values[1]).label();

        return ValueOrError.General.of(literal(startsWithIgnoreCase(string, prefix)));
    }

    @Override
    public StartsWithIgnoreCase copy() {
        return new StartsWithIgnoreCase(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.startsWithIgnoreCase.name();
    }
}
