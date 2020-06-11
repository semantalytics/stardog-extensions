package com.semantalytics.stardog.kibble.geo.geohash;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.github.davidmoten.geo.GeoHash.decodeHash;
import static com.stardog.stark.Values.literal;

public final class Longitude extends AbstractFunction implements UserDefinedFunction {

    protected Longitude() {
        super(1, GeoHashVocabulary.longitude.iri.toString());
    }

    private Longitude(final Longitude longitude) {
        super(longitude);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String hash = ((Literal)values[0]).label();

            return ValueOrError.General.of(literal(decodeHash(hash).getLon()));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Longitude copy() {
        return new Longitude(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return GeoHashVocabulary.longitude.name();
    }

}
