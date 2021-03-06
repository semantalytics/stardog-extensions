package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.RegExUtils.*;

public final class ReplaceFirst extends AbstractFunction implements StringFunction {

    protected ReplaceFirst() {
        super(3, StringVocabulary.replaceFirst.toString());
    }

    private ReplaceFirst(final ReplaceFirst replaceFirst) {
        super(replaceFirst);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        for(final Value value : values) {
            if(!assertStringLiteral(value)) {
                return ValueOrError.Error;
            }
        }

        final String string = ((Literal)values[0]).label();
        final String searchString = ((Literal)values[1]).label();
        final String replacement = ((Literal)values[2]).label();

        return ValueOrError.General.of(literal(replaceFirst(string, searchString, replacement)));
    }

    @Override
    public ReplaceFirst copy() {
        return new ReplaceFirst(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.replaceFirst.toString();
    }
}
