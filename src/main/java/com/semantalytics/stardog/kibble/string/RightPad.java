

package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class RightPad extends AbstractFunction implements StringFunction {

    protected RightPad() {
        super(2, StringVocabulary.rightPad.toString());
    }

    private RightPad(final RightPad rightPad) {
        super(rightPad);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        final ValueOrError result;

        if(assertStringLiteral(values[0]) && assertIntegerLiteral(values[1])) {

            final String string = ((Literal) values[0]).label();
            final int size = Literal.intValue((Literal) values[1]);

            result = ValueOrError.General.of(literal(rightPad(string, size)));
        } else {
            result =  ValueOrError.Error;
        }

        return result;
    }

    @Override
    public RightPad copy() {
        return new RightPad(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.rightPad.toString();
    }
}
