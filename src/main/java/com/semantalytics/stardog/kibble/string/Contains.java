package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.apache.commons.lang3.StringUtils;

import static com.stardog.stark.Values.literal;

public final class Contains extends AbstractFunction implements StringFunction {

    protected Contains() {
        super(2, StringVocabulary.contains.toString());
    }

    private Contains(final Contains contains) {
        super(contains);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(!assertStringLiteral(values[0]) || !assertStringLiteral(values[1])) {
            return ValueOrError.Error;
        }

        final String sequence = ((Literal)values[0]).label();
        final String searchSequence = ((Literal)values[1]).label();

        return ValueOrError.General.of(literal(StringUtils.contains(sequence, searchSequence)));
    }

    @Override
    public Contains copy() {
        return new Contains(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.contains.toString();
    }
}
