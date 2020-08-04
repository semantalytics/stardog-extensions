package com.semantalytics.stardog.kibble.geo.geohash;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.github.davidmoten.geo.GeoHash.*;
import static com.stardog.stark.Values.literal;

public final class Bottom extends AbstractFunction implements UserDefinedFunction {

    protected Bottom() {
        super(1, GeoHashVocabulary.bottom.toString());
    }

    private Bottom(final Bottom bottom) {
        super(bottom);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String hash = ((Literal)values[0]).label();

            return ValueOrError.General.of(literal(bottom(hash)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Bottom copy() {
        return new Bottom(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return GeoHashVocabulary.bottom.toString();
    }

}
