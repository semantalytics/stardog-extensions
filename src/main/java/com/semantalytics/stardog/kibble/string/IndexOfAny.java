package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class IndexOfAny extends AbstractFunction implements StringFunction {

    protected IndexOfAny() {
        super(2, StringVocabulary.indexOfAny.toString());
    }

    private IndexOfAny(final IndexOfAny indexOfAny) {
        super(indexOfAny);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {

            final String string = ((Literal) values[0]).label();
            final String searchChars = ((Literal) values[1]).label();

            return ValueOrError.General.of(literal(indexOfAny(string, searchChars)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public IndexOfAny copy() {
        return new IndexOfAny(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.indexOfAny.name();
    }
}
