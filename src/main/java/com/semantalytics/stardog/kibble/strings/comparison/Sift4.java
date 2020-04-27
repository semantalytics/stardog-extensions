package com.semantalytics.stardog.kibble.strings.comparison;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

public final class Sift4 extends AbstractFunction implements StringFunction {

    private static final info.debatty.java.stringsimilarity.experimental.Sift4 sift4;

    static {
        sift4 = new info.debatty.java.stringsimilarity.experimental.Sift4();
    }

    protected Sift4() {
        super(Range.closed(2, 3), StringMetricVocabulary.sift4.stringValue());
    }

    private Sift4(final Sift4 sift4) {
        super(sift4);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        //TODO handle two arguments
        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {

            final String string1 = ((Literal)values[0]).label();
            final String string2 = ((Literal)values[1]).label();

            if(values.length == 3) {
                if(assertNumericLiteral(values[2])) {
                    sift4.setMaxOffset(Literal.intValue((Literal)values[2]));
                } else {
                    return ValueOrError.Error;
                }
            }

            return ValueOrError.Double.of(sift4.distance(string1, string2));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Sift4 copy() {
        return new Sift4(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.sift4.name();
    }
}
