package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class ContainsWhitespace extends AbstractFunction implements StringFunction {

    protected ContainsWhitespace() {
        super(1, StringVocabulary.containsWhitespace.toString());
    }

    private ContainsWhitespace(final ContainsWhitespace containsWhitespace) {
        super(containsWhitespace);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if (!assertStringLiteral(values[0])) {
            return ValueOrError.Error;
        }

        final String string = ((Literal)values[0]).label();

        return ValueOrError.General.of(literal(containsWhitespace(string)));
    }

    @Override
    public ContainsWhitespace copy() {
        return new ContainsWhitespace(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.containsWhitespace.toString();
    }
}
