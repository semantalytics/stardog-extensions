package com.semantalytics.stardog.kibble.geo.geohash;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.github.davidmoten.geo.GeoHash.widthDegrees;
import static com.stardog.stark.Values.literal;

public final class WidthDegrees extends AbstractFunction implements UserDefinedFunction {

    protected WidthDegrees() {
        super(1, GeoHashVocabulary.widthDegrees.toString());
    }

    private WidthDegrees(final WidthDegrees widthDegrees) {
        super(widthDegrees);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertNumericLiteral(values[0])) {
            final int length = Literal.intValue((Literal)values[0]);

            return ValueOrError.General.of(literal(widthDegrees(length)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public WidthDegrees copy() {
        return new WidthDegrees(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return GeoHashVocabulary.widthDegrees.toString();
    }

}
