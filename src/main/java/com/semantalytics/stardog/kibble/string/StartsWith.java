package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class StartsWith extends AbstractFunction implements StringFunction {

    protected StartsWith() {
        super(2, StringVocabulary.startsWith.toString());
    }

    private StartsWith(final StartsWith startsWith) {
        super(startsWith);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {

            final String string = ((Literal) values[0]).label();
            final String prefix = ((Literal) values[1]).label();

            return ValueOrError.General.of(literal(startsWith(string, prefix)));

        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public StartsWith copy() {
        return new StartsWith(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.startsWith.toString();
    }
}
