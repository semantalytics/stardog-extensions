package com.semantalytics.stardog.kibble.array;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Datatype;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;

public final class Offset extends AbstractFunction implements StringFunction {

    protected Offset() {
        super(1, ArrayVocabulary.offset.toString());
    }

    private Offset(final Offset offset) {
        super(offset);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if (assertNumericLiteral(values[0])) {

            int index = Literal.intValue((Literal) values[0]);

            return ValueOrError.General.of(literal(String.valueOf(index), ArrayVocabulary.offsetDatatype.iri));

        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Offset copy() {
        return new Offset(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.offset.name();
    }
}
