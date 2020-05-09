package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class LeftPad extends AbstractFunction implements StringFunction {

    protected LeftPad() {
        super(Range.closed(2, 3), StringVocabulary.leftPad.toString());
    }

    private LeftPad(final LeftPad leftPad) {
        super(leftPad);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(!assertStringLiteral(values[0]) || !assertNumericLiteral(values[1])) {
            return ValueOrError.Error;
        }

        final String string = ((Literal)values[0]).label();
        final int size = Literal.intValue((Literal)values[1]);

        switch(values.length) {
            case 2: {
                return ValueOrError.General.of(literal(leftPad(string, size)));
            }
            case 3: {
                if(!assertStringLiteral(values[2])) {
                    return ValueOrError.Error;
                }

                final String padStr = ((Literal)values[2]).label();
                return ValueOrError.General.of(literal(leftPad(string, size, padStr)));
            }
            default:
            {
                return ValueOrError.Error;
            }
        }
    }

    @Override
    public LeftPad copy() {
        return new LeftPad(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.leftPad.name();
    }
}
