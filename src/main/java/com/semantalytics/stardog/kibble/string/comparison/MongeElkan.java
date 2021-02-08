package com.semantalytics.stardog.kibble.string.comparison;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.Constant;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

public final class MongeElkan extends AbstractFunction implements StringFunction {

    private info.debatty.java.stringsimilarity.Cosine cosine;

    protected MongeElkan() {
        super(Range.closed(2, 3), StringMetricVocabulary.mongeElkan.toString());
    }

    private MongeElkan(final MongeElkan mongeElkan) {
        super(mongeElkan);
        this.cosine = mongeElkan.cosine;
    }

    @Override
    public void initialize() {
        cosine = null;
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {

            final String string1 = ((Literal)values[0]).label();
            final String string2 = ((Literal)values[1]).label();

            if(cosine == null) {
                if (values.length == 3 && assertNumericLiteral(values[2]) && values[2] instanceof Constant) {
                    final int n = Literal.intValue((Literal)values[2]);
                    cosine = new info.debatty.java.stringsimilarity.Cosine(n);
                } else {
                    cosine = new info.debatty.java.stringsimilarity.Cosine();
                }
            } else {
                return ValueOrError.Error;
            }

            return ValueOrError.Double.of(cosine.distance(string1, string2));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public MongeElkan copy() {
        return new MongeElkan(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.cosineDistance.toString();
    }
}
