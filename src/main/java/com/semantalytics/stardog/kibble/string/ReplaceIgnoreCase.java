package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class ReplaceIgnoreCase extends AbstractFunction implements StringFunction {

    protected ReplaceIgnoreCase() {
        super(3, StringVocabulary.replaceIgnoreCase.toString());
    }

    private ReplaceIgnoreCase(final ReplaceIgnoreCase replaceIgnoreCase) {
        super(replaceIgnoreCase);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(!assertStringLiteral(values[0]) || !assertStringLiteral(values[1]) || !assertStringLiteral(values[2])) {
            return ValueOrError.Error;
        }

        final String string = ((Literal)values[0]).label();
        final String searchString = ((Literal)values[1]).label();
        final String replacement = ((Literal)values[2]).label();

        return ValueOrError.General.of(literal(replaceIgnoreCase(string, searchString, replacement)));
    }

    @Override
    public ReplaceIgnoreCase copy() {
        return new ReplaceIgnoreCase(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.replaceIgnoreCase.name();
    }
}
