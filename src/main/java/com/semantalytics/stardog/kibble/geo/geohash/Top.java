package com.semantalytics.stardog.kibble.geo.geohash;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.github.davidmoten.geo.GeoHash.*;
import static com.stardog.stark.Values.literal;

public final class Top extends AbstractFunction implements UserDefinedFunction {

    protected Top() {
        super(1, GeoHashVocabulary.top.toString());
    }

    private Top(final Top top) {
        super(top);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String hash = ((Literal)values[0]).label();

            return ValueOrError.General.of(literal(top(hash)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Top copy() {
        return new Top(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return GeoHashVocabulary.top.toString();
    }

}
