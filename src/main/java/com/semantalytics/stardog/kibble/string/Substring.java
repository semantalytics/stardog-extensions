package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class Substring extends AbstractFunction implements StringFunction {

    protected Substring() {
        super(2, StringVocabulary.substring.toString());
    }

    private Substring(final Substring substring) {
        super(substring);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0]) && assertIntegerLiteral(values[1])) {

            final String string = ((Literal) values[0]).label();
            final int start = Literal.intValue((Literal) values[1]);

            return ValueOrError.General.of(literal(substring(string, start)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Substring copy() {
        return new Substring(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.substring.toString();
    }
}
