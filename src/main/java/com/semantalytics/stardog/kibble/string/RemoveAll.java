package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.RegExUtils.*;

public final class RemoveAll extends AbstractFunction implements StringFunction {

    protected RemoveAll() {
        super(2, StringVocabulary.removeAll.toString());
    }

    private RemoveAll(final RemoveAll removeAll) {
        super(removeAll);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(!assertStringLiteral(values[0]) || !assertStringLiteral(values[1])) {
            return ValueOrError.Error;
        }

        final String string = ((Literal)values[0]).label();
        final String regex = ((Literal)values[1]).label();

        return ValueOrError.General.of(literal(removeAll(string, regex)));
    }

    @Override
    public RemoveAll copy() {
        return new RemoveAll(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.removeAll.name();
    }
}
