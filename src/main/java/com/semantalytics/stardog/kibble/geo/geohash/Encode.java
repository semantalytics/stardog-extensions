package com.semantalytics.stardog.kibble.geo.geohash;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.github.davidmoten.geo.GeoHash.*;
import static com.stardog.stark.Values.literal;

public final class Encode extends AbstractFunction implements UserDefinedFunction {

    protected Encode() {
        super(2, GeoHashVocabulary.encode.toString());
    }

    private Encode(final Encode encode) {
        super(encode);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        if(assertNumericLiteral(values[0]) && assertNumericLiteral(values[1])) {

            final double latitude = Literal.doubleValue((Literal)values[0]);
            final double longitude = Literal.doubleValue((Literal)values[1]);

            return ValueOrError.General.of(literal(encodeHash(latitude, longitude)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Function copy() {
        return new Encode(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return GeoHashVocabulary.encode.toString();
    }

}
