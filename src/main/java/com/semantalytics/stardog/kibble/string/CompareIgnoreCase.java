package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.*;
import static org.apache.commons.lang3.StringUtils.*;

public final class CompareIgnoreCase extends AbstractFunction implements StringFunction {

    protected CompareIgnoreCase() {
        super(2, StringVocabulary.compareIgnoreCase.toString());
    }

    private CompareIgnoreCase(final CompareIgnoreCase compareIgnoreCase) {
        super(compareIgnoreCase);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {

            final String firstString = ((Literal) values[0]).label();
            final String secondString = ((Literal) (values[1])).label();

            return ValueOrError.General.of(literal(compareIgnoreCase(firstString, secondString)));
        } else {
            return ValueOrError.Error;
        }

    }

    @Override
    public CompareIgnoreCase copy() {
        return new CompareIgnoreCase(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.compareIgnoreCase.name();
    }
}
