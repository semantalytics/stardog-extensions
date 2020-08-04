package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class SubstringBeforeLast extends AbstractFunction implements StringFunction {

    protected SubstringBeforeLast() {
        super(2, StringVocabulary.substringBeforeLast.toString());
    }

    private SubstringBeforeLast(final SubstringBeforeLast substringBeforeLast) {
        super(substringBeforeLast);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(!assertStringLiteral(values[0]) || !assertStringLiteral(values[1])) {
            return ValueOrError.Error;
        }

        final String string = ((Literal)values[0]).label();
        final String separator = ((Literal)values[1]).label();

        return ValueOrError.General.of(literal(substringBeforeLast(string, separator)));
    }

    @Override
    public SubstringBeforeLast copy() {
        return new SubstringBeforeLast(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.substringBeforeLast.toString();
    }
}
