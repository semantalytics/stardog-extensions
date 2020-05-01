package com.semantalytics.stardog.kibble.string.comparison;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.Constant;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

public final class NGram extends AbstractFunction implements StringFunction {

    private info.debatty.java.stringsimilarity.NGram nGram;

    protected NGram() {
        super(Range.closed(2, 3), StringMetricVocabulary.ngram.stringValue());
    }

    private NGram(final NGram nGram) {
        super(nGram);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {

            final String string1 = ((Literal)values[0]).label();
            final String string2 = ((Literal)values[1]).label();

            if (values.length == 3 && assertNumericLiteral(values[2]) && values[2] instanceof Constant) {

                final int n = Integer.parseInt(((Constant) getArgs().get(2)).getValue().stringValue());
                if(nGram == null) {
                    nGram = new info.debatty.java.stringsimilarity.NGram(n);
                }
            } else {
                if(nGram == null) {
                    nGram = new info.debatty.java.stringsimilarity.NGram();
                }
            }

            return ValueOrError.Double.of(nGram.distance(string1, string2));
        } else {
            return ValueOrError.Error;
        }
    }

    public Function copy() {
        NGram that = new NGram(this);
        that.nGram = this.nGram;
        return that;
    }

    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.needlemanWunch.name();
    }
}
