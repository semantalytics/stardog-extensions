package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.util.Arrays;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class StartsWithAny extends AbstractFunction implements StringFunction {

    protected StartsWithAny() {
        super(Range.atLeast(2), StringVocabulary.startsWithAny.toString());
    }

    private StartsWithAny(final StartsWithAny startsWithAny) {
        super(startsWithAny);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        for(final Value value : values) {
            if(!assertStringLiteral(value)) {
                return ValueOrError.Error;
            }
        }

        final String string = ((Literal)values[0]).label();
        final String[] prefix = Arrays.stream(values).skip(1).map(v -> (Literal)v).map(Literal::label).toArray(String[]::new);

        return ValueOrError.General.of(literal(startsWithAny(string, prefix)));
    }

    @Override
    public StartsWithAny copy() {
        return new StartsWithAny(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.startsWithAny.toString();
    }
}
