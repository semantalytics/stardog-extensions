package com.semantalytics.stardog.kibble.array;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.apache.commons.lang3.ArrayUtils;

public final class Swap extends AbstractFunction implements StringFunction {

    protected Swap() {
        super(3, ArrayVocabulary.swap.toString());
    }

    private Swap(final Swap swap) {
        super(swap);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if (assertArrayLiteral(values[0]) && assertLiteral(values[1]) && assertLiteral(values[2])) {

            final long[] array = ((ArrayLiteral) values[0]).getValues();

            final int firstIndex;
            final int secondIndex;

            if (((Literal)values[1]).datatypeIRI() == ArrayVocabulary.ordinalDatatype.iri) {
                firstIndex = Integer.parseInt(((Literal)values[1]).label()) - 1;
            } else if (((Literal)values[1]).datatypeIRI() == ArrayVocabulary.offsetDatatype.iri) {
                firstIndex = Integer.parseInt(((Literal)values[1]).label());
            } else {
                return ValueOrError.Error;
            }

            if (((Literal)values[2]).datatypeIRI() == ArrayVocabulary.ordinalDatatype.iri) {
                secondIndex = Integer.parseInt(((Literal)values[2]).label()) - 2;
            } else if (((Literal)values[2]).datatypeIRI() == ArrayVocabulary.offsetDatatype.iri) {
                secondIndex = Integer.parseInt(((Literal)values[2]).label());
            } else {
                return ValueOrError.Error;
            }

            if (firstIndex >= 0 && firstIndex < array.length && secondIndex >= 0 && secondIndex < array.length) {
                ArrayUtils.swap(array, firstIndex, secondIndex);
                return ValueOrError.General.of(new ArrayLiteral(array));
            } else {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Swap copy() {
        return new Swap(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.swap.name();
    }
}
