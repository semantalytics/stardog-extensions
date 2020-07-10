package com.semantalytics.stardog.kibble.array;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

public final class Sub extends AbstractFunction implements StringFunction {

    protected Sub() {
        super(Range.closed(2, 3), ArrayVocabulary.sub.toString());
    }

    private Sub(final Sub sub) {
        super(sub);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        if (assertArrayLiteral(values[0]) && assertLiteral(values[1]) && assertLiteral(values[2])) {

            final long[] array = ((ArrayLiteral) values[0]).getValues();

            final int startIndexInclusive;
            final int endIndexExclusive;

            if (((Literal)values[1]).datatypeIRI() == ArrayVocabulary.ordinalDatatype) {
                startIndexInclusive = Integer.parseInt(((Literal)values[1]).label()) - 1;
            } else if (((Literal)values[1]).datatypeIRI() == ArrayVocabulary.offsetDatatype) {
                startIndexInclusive = Integer.parseInt(((Literal)values[1]).label());
            } else {
                return ValueOrError.Error;
            }

            if (((Literal)values[2]).datatypeIRI() == ArrayVocabulary.ordinalDatatype) {
                endIndexExclusive = Integer.parseInt(((Literal)values[2]).label());
            } else if (((Literal)values[2]).datatypeIRI() == ArrayVocabulary.offsetDatatype) {
                endIndexExclusive = Integer.parseInt(((Literal)values[2]).label());
            } else {
                return ValueOrError.Error;
            }

            if (startIndexInclusive >= 0 && startIndexInclusive <= array.length && endIndexExclusive >= 0 && endIndexExclusive <= array.length) {
                return ValueOrError.General.of(new ArrayLiteral(ArrayUtils.subarray(array, startIndexInclusive, endIndexExclusive)));
            } else {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Sub copy() {
        return new Sub(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.sub.toString();
    }
}
