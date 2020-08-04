package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class AbbreviateMiddle extends AbstractFunction implements StringFunction {

    protected AbbreviateMiddle() {
        super(3, StringVocabulary.abbreviateMiddle.toString());
    }

    private AbbreviateMiddle(final AbbreviateMiddle abbreviateMiddle) {
        super(abbreviateMiddle);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if (!assertStringLiteral(values[0]) || !assertStringLiteral(values[1]) || !assertIntegerLiteral(values[2])) {
            return ValueOrError.Error;
        }

        final String string = ((Literal) values[0]).label();
        final String middle = ((Literal) values[1]).label();
        final int length = Literal.intValue((Literal) values[2]);

        return ValueOrError.General.of(literal(abbreviateMiddle(string, middle, length)));
    }

    @Override
    public AbbreviateMiddle copy() {
        return new AbbreviateMiddle(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.abbreviateMiddle.toString();
    }
}
