package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.*;
import static org.apache.commons.lang3.StringUtils.*;

public final class CountMatches extends AbstractFunction implements StringFunction {

    protected CountMatches() {
        super(2, StringVocabulary.countMatches.toString());
    }

    private CountMatches(final CountMatches countMatches) {
        super(countMatches);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        final ValueOrError result;

        if (assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {

            final String string = ((Literal) values[0]).label();
            final String sequence = ((Literal) values[1]).label();

            result = ValueOrError.General.of(literal(countMatches(string, sequence)));

        } else {
            result = ValueOrError.Error;
        }
        return result;
    }

    @Override
    public CountMatches copy() {
        return new CountMatches(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.countMatches.toString();
    }
}
