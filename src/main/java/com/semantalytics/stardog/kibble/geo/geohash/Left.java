package com.semantalytics.stardog.kibble.geo.geohash;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.github.davidmoten.geo.GeoHash.*;
import static com.stardog.stark.Values.literal;

public final class Left extends AbstractFunction implements UserDefinedFunction {

    protected Left() {
        super(1, GeoHashVocabulary.left.stringValue());
    }

    private Left(final Left left) {
        super(left);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String hash = ((Literal)values[0]).label();

            return ValueOrError.General.of(literal(left(hash)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Left copy() {
        return new Left(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return GeoHashVocabulary.left.name();
    }

}
