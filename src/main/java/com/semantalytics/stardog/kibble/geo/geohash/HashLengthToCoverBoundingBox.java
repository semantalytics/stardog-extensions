package com.semantalytics.stardog.kibble.geo.geohash;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.github.davidmoten.geo.GeoHash.hashLengthToCoverBoundingBox;
import static com.stardog.stark.Values.literal;

public final class HashLengthToCoverBoundingBox extends AbstractFunction implements UserDefinedFunction {

    protected HashLengthToCoverBoundingBox() {
        super(4, GeoHashVocabulary.hashLengthToCoverBoundingBox.stringValue());
    }

    private HashLengthToCoverBoundingBox(final HashLengthToCoverBoundingBox hashLengthToCoverBoundingBox) {
        super(hashLengthToCoverBoundingBox);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertNumericLiteral(values[0]) &&
        assertNumericLiteral(values[1]) &&
        assertNumericLiteral(values[2]) &&
        assertNumericLiteral(values[3])) {

            final double topLeftLatitude = Literal.doubleValue((Literal) values[0]);
            final double topLeftLongitude = Literal.doubleValue((Literal) values[1]);
            final double bottomRightLatitude = Literal.doubleValue((Literal) values[2]);
            final double bottomRightLongitude = Literal.doubleValue((Literal) values[3]);

            return ValueOrError.General.of(literal(hashLengthToCoverBoundingBox(topLeftLatitude, topLeftLongitude, bottomRightLatitude, bottomRightLongitude)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public HashLengthToCoverBoundingBox copy() {
        return new HashLengthToCoverBoundingBox(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return GeoHashVocabulary.hashLengthToCoverBoundingBox.name();
    }

}
