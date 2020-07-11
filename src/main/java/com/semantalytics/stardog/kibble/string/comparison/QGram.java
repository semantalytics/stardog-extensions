package com.semantalytics.stardog.kibble.string.comparison;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

public final class QGram extends AbstractFunction implements StringFunction {

    private info.debatty.java.stringsimilarity.QGram qGram;

    protected QGram() {
        super(Range.closed(2, 3), StringMetricVocabulary.qgram.toString());
    }

    private QGram(final QGram qGram) {
        super(qGram);
        this.qGram = qGram.qGram;
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if (assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {

            final String firstString = ((Literal) values[0]).label();
            final String secondString = ((Literal) values[1]).label();

            if (values.length == 3) {
                if (assertNumericLiteral(values[2])) {
                    final int n = Literal.intValue((Literal) values[2]);
                    if(qGram == null || qGram.getK() != n) {
                        qGram = new info.debatty.java.stringsimilarity.QGram(n);
                    }
                } else {
                    return ValueOrError.Error;
                }
            } else if (values.length == 2) {
                if(qGram == null) {
                    qGram = new info.debatty.java.stringsimilarity.QGram();
                }
            }
            return ValueOrError.Double.of(qGram.distance(firstString, secondString));
        } else {
            return ValueOrError.Error;
        }
    }

    public Function copy() {
        QGram that = new QGram(this);
        that.qGram = this.qGram;
        return that;
    }

    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.qgram.toString();
    }
}

