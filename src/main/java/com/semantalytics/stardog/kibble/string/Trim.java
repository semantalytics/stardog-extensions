package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class Trim extends AbstractFunction implements StringFunction {

    protected Trim() {
        super(1, StringVocabulary.trim.toString());
    }

    private Trim(final Trim trim) {
        super(trim);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String string = ((Literal) values[0]).label();
            return ValueOrError.General.of(literal(trim(string)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Trim copy() {
        return new Trim(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.trim.name();
    }
}
