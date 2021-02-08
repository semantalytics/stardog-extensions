package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class Mid extends AbstractFunction implements StringFunction {

    protected Mid() {
        super(3, StringVocabulary.mid.toString());
    }

    private Mid(final Mid mid) {
        super(mid);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if (!assertStringLiteral(values[0]) || !assertIntegerLiteral(values[1]) || !assertIntegerLiteral(values[2])) {
            return ValueOrError.Error;
        }

        final String string = ((Literal) values[0]).label();
        final int position = Literal.intValue((Literal) values[1]);
        final int length = Literal.intValue((Literal) values[2]);

        return ValueOrError.General.of(literal(mid(string, position, length)));
    }

    @Override
    public Mid copy() {
        return new Mid(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.mid.toString();
    }
}
