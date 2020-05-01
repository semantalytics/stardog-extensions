package com.complexible.stardog.plan.aggregates;

import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.semantalytics.stardog.kibble.visualization.ascii.AsciiVisualizationVocabulary;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;

public final class Boxplot extends AbstractAggregate {

    public Boxplot() {
        super(AsciiVisualizationVocabulary.boxplot.stringValue());
    }

    private Boxplot(final Boxplot boxplot) {
        super(boxplot);
    }

    @Override
    protected ValueOrError _getValue() {
        //TODO ???
        return null;
    }

    @Override
    protected ValueOrError aggregate(long l, Value value, Value... values) {
        return ValueOrError.General.of(literal(new de.davidm.textplots.Boxplot.BoxplotBuilder(data).plotObject().printPlot()));
    }

    @Override
    public Boxplot copy() {
        return new Boxplot(this);
    }
}
