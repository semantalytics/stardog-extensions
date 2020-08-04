package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class ReplaceChars extends AbstractFunction implements StringFunction {

    protected ReplaceChars() {
        super(3, StringVocabulary.replaceChars.toString());
    }

    private ReplaceChars(final ReplaceChars replaceChars) {
        super(replaceChars);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(!assertStringLiteral(values[0]) || !assertStringLiteral(values[1]) || !assertStringLiteral(values[2])) {
            return ValueOrError.Error;
        }

        final String string = ((Literal)values[0]).label();
        final String searchString = ((Literal)values[1]).label();
        final String replacement = ((Literal)values[2]).label();

        return ValueOrError.General.of(literal(replaceChars(string, searchString, replacement)));
    }

    @Override
    public ReplaceChars copy() {
        return new ReplaceChars(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.replaceChars.toString();
    }
}
