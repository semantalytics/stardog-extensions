package com.semantalytics.stardog.kibble.visualization.ascii;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.util.Arrays;
import java.util.List;

import static com.stardog.stark.Values.literal;

public final class Spark extends AbstractFunction implements StringFunction {

    private static final List<Character> ticks = Arrays.asList('\u2581','\u2582', '\u2583', '\u2584', '\u2585', '\u2586', '\u2587','\u2588');

    public Spark() {
        super(1, AsciiVisualizationVocabulary.spark.stringValue());
    }

    private Spark(final Spark spark) {
        super(spark);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertNumericLiteral(values[0])) {
            final int i = Literal.intValue((Literal)values[0]);

            if (i >= ticks.size() || i < 0) {
                return ValueOrError.Error;
            }

            return ValueOrError.General.of(literal(ticks.get(i).toString()));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public Spark copy() {
        return new Spark(this);
    }

    @Override
    public String toString() {
        return AsciiVisualizationVocabulary.spark.name();
    }

}
