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

public final class LastIndexOfIgnoreCase extends AbstractFunction implements StringFunction {

    protected LastIndexOfIgnoreCase() {
        super(Range.closed(2, 3), StringVocabulary.lastIndexOfIgnoreCase.toString());
    }

    private LastIndexOfIgnoreCase(final LastIndexOfIgnoreCase lastIndexOfIgnoreCase) {
        super(lastIndexOfIgnoreCase);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(!assertStringLiteral(values[0]) || !assertStringLiteral(values[1])) {
            return ValueOrError.Error;
        }

        final String string = ((Literal)values[0]).label();
        final String searchString = ((Literal)values[1]).label();

        switch(values.length) {
            case 2: {
                return ValueOrError.General.of(literal(StringUtils.lastIndexOfIgnoreCase(string, searchString)));
            }
            case 3: {
                if(!assertNumericLiteral(values[2])) {
                    return ValueOrError.Error;
                }
                final int startPos = Literal.intValue((Literal)values[2]);
                return ValueOrError.General.of(literal(StringUtils.lastIndexOfIgnoreCase(string, searchString, startPos)));
            }
            default:
                return ValueOrError.Error;
        }
    }

    @Override
    public LastIndexOfIgnoreCase copy() {
        return new LastIndexOfIgnoreCase(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.lastIndexOfIgnoreCase.toString();
    }
}
