package com.semantalytics.stardog.kibble.array;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;

public final class Ordinal extends AbstractFunction implements StringFunction {

    protected Ordinal() {
        super(1, ArrayVocabulary.ordinal.toString());
    }

    private Ordinal(final Ordinal ordinal) {
        super(ordinal);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if (assertNumericLiteral(values[0])) {

            int index = Literal.intValue((Literal) values[0]);

            return ValueOrError.General.of(literal(String.valueOf(index), ArrayVocabulary.ordinalDatatype));

        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Ordinal copy() {
        return new Ordinal(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.ordinal.toString();
    }
}
