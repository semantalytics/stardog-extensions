package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public class IndexOfIgnoreCase extends AbstractFunction implements StringFunction {

    protected IndexOfIgnoreCase() {
        super(2, StringVocabulary.indexOfIgnoreCase.toString());
    }

    private IndexOfIgnoreCase(final IndexOfIgnoreCase indexOfIgnoreCase) {
        super(indexOfIgnoreCase);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(!assertStringLiteral(values[0]) || !assertStringLiteral(values[1])) {
            return ValueOrError.Error;
        }
        final String string = ((Literal)values[0]).label();
        final String searchChars = ((Literal)values[1]).label();

        return ValueOrError.General.of(literal(indexOfIgnoreCase(string, searchChars)));
    }

    @Override
    public IndexOfIgnoreCase copy() {
        return new IndexOfIgnoreCase(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.indexOfIgnoreCase.name();
    }
}
