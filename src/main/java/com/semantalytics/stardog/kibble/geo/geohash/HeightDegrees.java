package com.semantalytics.stardog.kibble.geo.geohash;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.github.davidmoten.geo.GeoHash.*;
import static com.stardog.stark.Values.literal;

public final class HeightDegrees extends AbstractFunction implements UserDefinedFunction {

    protected HeightDegrees() {
        super(1, GeoHashVocabulary.heightDegrees.stringValue());
    }

    private HeightDegrees(final HeightDegrees heightDegrees) {
        super(heightDegrees);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertNumericLiteral(values[0])) {
            final int length = Literal.intValue((Literal) values[0]);

            return ValueOrError.General.of(literal(heightDegrees(length)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public HeightDegrees copy() {
        return new HeightDegrees(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return GeoHashVocabulary.heightDegrees.name();
    }

}
