package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.RegExUtils.*;

public final class RemoveFirst extends AbstractFunction implements StringFunction {

    protected RemoveFirst() {
        super(2, StringVocabulary.removeFirst.toString());
    }

    private RemoveFirst(final RemoveFirst removeFirst) {
        super(removeFirst);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(!assertStringLiteral(values[0]) || !assertStringLiteral(values[1])) {
            return ValueOrError.Error;
        }

        final String string = ((Literal)values[0]).label();
        final String regex = ((Literal)values[1]).label();

        return ValueOrError.General.of(literal(removeFirst(string, regex)));
    }

    @Override
    public RemoveFirst copy() {
        return new RemoveFirst(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.removeFirst.name();
    }
}
