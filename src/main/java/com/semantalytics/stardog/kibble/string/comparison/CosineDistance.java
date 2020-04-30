package com.semantalytics.stardog.kibble.string.comparison;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

public final class CosineDistance extends AbstractFunction implements StringFunction {

    private info.debatty.java.stringsimilarity.Cosine cosine = null;

    protected CosineDistance() {
        super(Range.closed(2, 3), StringMetricVocabulary.cosineDistance.stringValue());
    }

    private CosineDistance(final CosineDistance cosineDistance) {
        super(cosineDistance);
        this.cosine = cosineDistance.cosine;
    }

    @Override
    public void initialize() {
        cosine = null;
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if (assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {

            final String string1 = ((Literal) values[0]).label();
            final String string2 = ((Literal) values[1]).label();

            if (values.length == 3) {
                if(assertNumericLiteral(values[2])) {
                    final int n = Literal.intValue((Literal) values[2]);
                    if(cosine == null || cosine.getK() != n) {
                        cosine = new info.debatty.java.stringsimilarity.Cosine(n);
                    }
                } else {
                    return ValueOrError.Error;
                }
            } else if (values.length == 2 && cosine == null) {
                cosine = new info.debatty.java.stringsimilarity.Cosine();
            }

            return ValueOrError.Double.of(cosine.distance(string1, string2));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public CosineDistance copy() {
        return new CosineDistance(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.cosineDistance.name();
    }
}
