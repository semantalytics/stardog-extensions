package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.*;
import static org.apache.commons.lang3.StringUtils.*;

public final class ContainsIgnoreCase extends AbstractFunction implements StringFunction {

    protected ContainsIgnoreCase() {
        super(2, StringVocabulary.containsIgnoreCase.toString());
    }

    private ContainsIgnoreCase(final ContainsIgnoreCase containsIgnoreCase) {
        super(containsIgnoreCase);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(!assertStringLiteral(values[0]) || !assertStringLiteral(values[1])) {
            return ValueOrError.Error;
        }
        final String sequence = ((Literal)values[0]).label();
        final String searchSequence = ((Literal)values[1]).label();

        return ValueOrError.General.of(literal(containsIgnoreCase(sequence, searchSequence)));
    }

    @Override
    public ContainsIgnoreCase copy() {
        return new ContainsIgnoreCase(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.containsIgnoreCase.name();
    }
}
