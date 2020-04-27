package com.complexible.stardog.plan.aggregates;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.semantalytics.stardog.kibble.visualization.ascii.AsciiVisualizationVocabulary;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;

public final class Boxplot extends AbstractAggregate {

    public Boxplot() {
        super(AsciiVisualizationVocabulary.boxplot.stringValue());
    }

    private Boxplot(final Boxplot boxplot) {
        super(boxplot);
    }

    @Override
    protected Value _getValue() throws ExpressionEvaluationException {
        return null;
    }

    @Override
    protected void aggregate(long l, Value value, Value... values) throws ExpressionEvaluationException {
        return literal(new de.davidm.textplots.Boxplot.BoxplotBuilder(data).plotObject().printPlot());
    }

    @Override
    public Boxplot copy() {
        return new Boxplot(this);
    }
}
