package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class Digits extends AbstractFunction implements StringFunction {

    protected Digits() {
        super(1, StringVocabulary.digits.toString());
    }

    private Digits(final Digits digits) {
        super(digits);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(!assertStringLiteral(values[0])) {
            return ValueOrError.Error;
        }

        final String string = ((Literal)values[0]).label();

      return ValueOrError.General.of(literal(getDigits(string)));
    }

    @Override
    public Digits copy() {
        return new Digits(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.digits.toString();
    }
}
