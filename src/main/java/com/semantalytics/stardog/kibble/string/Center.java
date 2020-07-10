package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.apache.commons.lang3.StringUtils;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class Center extends AbstractFunction implements StringFunction {

    protected Center() {
        super(Range.closed(2, 3), StringVocabulary.center.toString());
    }

    private Center(final Center center) {
        super(center);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        final ValueOrError result;

        if(assertStringLiteral(values[0]) && assertIntegerLiteral(values[1])) {

            final String string = ((Literal)values[0]).label();
            final int size = Literal.intValue((Literal)values[1]);

            switch(values.length) {
                case 2:
                    result =  ValueOrError.General.of(literal(center(string, size)));
                    break;
                case 3:
                    if (assertStringLiteral(values[2]) && ((Literal) values[2]).label().length() == 1) {
                        char padChar = ((Literal) values[2]).label().charAt(0);
                        result =  ValueOrError.General.of(literal(center(string, size, padChar)));
                    } else {
                        result =  ValueOrError.Error;
                    }
                    break;
                default:
                    result = ValueOrError.Error;
            }
        } else {
            result =  ValueOrError.Error;
        }
        return result;
    }

    @Override
    public Center copy() {
        return new Center(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.center.toString();
    }
}
