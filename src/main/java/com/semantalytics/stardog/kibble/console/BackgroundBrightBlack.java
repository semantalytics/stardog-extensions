package com.semantalytics.stardog.kibble.console;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Range;
import org.fusesource.jansi.Ansi;
import org.openrdf.model.Value;

import java.util.stream.Stream;

import static com.complexible.common.rdf.model.Values.literal;
import static org.fusesource.jansi.Ansi.Color.BLACK;
import static org.fusesource.jansi.Ansi.Color.DEFAULT;
import static org.fusesource.jansi.Ansi.ansi;

public class BackgroundBrightBlack extends AbstractFunction implements UserDefinedFunction {

    public BackgroundBrightBlack() {
        super(Range.all(), ConsoleVocabulary.backgroundBrightBlack.stringValue());
    }

    public BackgroundBrightBlack(final BackgroundBrightBlack console) {
        super(console);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
        final Ansi ansi = ansi();
        ansi.bgBright(BLACK);

        Stream.of(values).forEach(v -> ansi.a(v.stringValue()));

        if(values.length != 0) {
            ansi.bgBright(DEFAULT);
        }

        return literal(ansi.toString());
    }

    @Override
    public BackgroundBrightBlack copy() {
        return new BackgroundBrightBlack(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ConsoleVocabulary.backgroundBrightBlack.name();
    }
}
