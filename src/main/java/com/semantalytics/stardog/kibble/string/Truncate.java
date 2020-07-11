package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class Truncate extends AbstractFunction implements StringFunction {

    protected Truncate() {
        super(2, StringVocabulary.truncate.toString());
    }

    private Truncate(final Truncate truncate) {
        super(truncate);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0]) && assertIntegerLiteral(values[1])) {

            final String string = ((Literal) values[0]).label();
            final int maxWidth = Literal.intValue((Literal) values[1]);

            return ValueOrError.General.of(literal(truncate(string, maxWidth)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Truncate copy() {
        return new Truncate(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.truncate.toString();
    }
}
