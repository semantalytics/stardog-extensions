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

public final class IsNoneEmpty extends AbstractFunction implements StringFunction {

    protected IsNoneEmpty() {
        super(Range.atLeast(1), StringVocabulary.isNoneEmpty.toString());
    }

    private IsNoneEmpty(final IsNoneEmpty isNoneBlank) {
        super(isNoneBlank);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        for(final Value value : values) {
            if(!assertStringLiteral(value)) {
                return ValueOrError.Error;
            }
        }

        final String[] strings = Arrays.stream(values).map(v -> (Literal)v).map(Literal::label).toArray(String[]::new);

        return ValueOrError.General.of(literal(isNoneEmpty(strings)));
    }

    @Override
    public IsNoneEmpty copy() {
        return new IsNoneEmpty(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.isNoneBlank.toString();
    }
}
