package com.semantalytics.stardog.kibble.visualization.ascii;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import org.openrdf.model.Value;

import java.util.Arrays;
import java.util.List;

import static com.complexible.common.rdf.model.Values.*;

public final class Spark extends AbstractFunction implements StringFunction {

    private static final List<Character> ticks = Arrays.asList('\u2581','\u2582', '\u2583', '\u2584', '\u2585', '\u2586', '\u2587','\u2588');

    public Spark() {
        super(1, AsciiVisualizationVocabulary.spark.stringValue());
    }

    private Spark(final Spark spark) {
        super(spark);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final int i = assertNumericLiteral(values[0]).intValue();

        if(i >= ticks.size() || i < 0) {
            throw new ExpressionEvaluationException("Spark function takes arguments between 0 and 7. Found " + i);
        }

        return literal(ticks.get(i).toString());
    }

    public Value evaluate(final Value... values) throws ExpressionEvaluationException {
        this.assertRequiredArgs(values.length);
        return this.internalEvaluate(values);
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
