package com.semantalytics.stardog.kibble.geo.geohash;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.github.davidmoten.geo.GeoHash.hashContains;
import static com.stardog.stark.Values.literal;

public final class HashContains extends AbstractFunction implements UserDefinedFunction {

    protected HashContains() {
        super(3, GeoHashVocabulary.hashContains.stringValue());
    }

    private HashContains(final HashContains hashContains) {
        super(hashContains);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0]) && assertNumericLiteral(values[1]) && assertNumericLiteral(values[2])) {

            final String hash = ((Literal)values[0]).label();
            final double latitude = Literal.doubleValue((Literal)values[1]);
            final double longitude = Literal.doubleValue((Literal)values[2]);

            return ValueOrError.General.of(literal(hashContains(hash, latitude, longitude)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public HashContains copy() {
        return new HashContains(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return GeoHashVocabulary.hashContains.name();
    }

}
