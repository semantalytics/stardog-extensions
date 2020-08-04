package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.*;
import static org.apache.commons.lang3.StringUtils.*;

public final class AppendIfMissing extends AbstractFunction implements StringFunction {

    protected AppendIfMissing() {
        super(Range.atLeast(2), StringVocabulary.appendIfMissing.toString());
    }

    private AppendIfMissing(final AppendIfMissing appendIfMissing) {
        super(appendIfMissing);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(!assertStringLiteral(values[0]) || !assertStringLiteral(values[1])) {
            return ValueOrError.Error;
        }

        final String string = ((Literal)values[0]).label();
        final String suffix = ((Literal)values[1]).label();

        switch(values.length) {

            case 2: {
                return ValueOrError.General.of(literal(appendIfMissing(string, suffix)));
            }
            default: {
                final String[] suffixes = new String[values.length - 2];

                for(int i = 2; i < values.length; i++) {
                    if(!assertStringLiteral(values[i])) {
                        return ValueOrError.Error;
                    } else {
                        suffixes[i - 2] = ((Literal) values[i]).label();
                    }
                }

                return ValueOrError.General.of(literal(appendIfMissing(string, suffix, suffixes)));
            }
        }
    }

    @Override
    public AppendIfMissing copy() {
        return new AppendIfMissing(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.appendIfMissing.toString();
    }
}
