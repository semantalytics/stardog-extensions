package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class Uncapitalize extends AbstractFunction implements StringFunction {

    protected Uncapitalize() {
        super(1, StringVocabulary.uncapitalize.toString());
    }

    private Uncapitalize(final Uncapitalize uncapitalize) {
        super(uncapitalize);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if (!assertStringLiteral(values[0])) {
            return ValueOrError.Error;
        }

        final String string = ((Literal) values[0]).label();

        return ValueOrError.General.of(literal(uncapitalize(string)));
    }

    @Override
    public Uncapitalize copy() {
        return new Uncapitalize(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.uncapitalize.toString();
    }
}
