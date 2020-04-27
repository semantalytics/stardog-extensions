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
import static org.fusesource.jansi.Ansi.Color;
import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

public class BackgroundDefault extends AbstractFunction implements UserDefinedFunction {

    public BackgroundDefault() {
        super(Range.all(), ConsoleVocabulary.backgroundDefault.stringValue());
    }

    public BackgroundDefault(final BackgroundDefault console) {
        super(console);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
        final Ansi ansi = ansi();
        ansi.bg(DEFAULT);

        Stream.of(values).forEach(v -> ansi.a(v.stringValue()));

        if(values.length != 0) {
            ansi.bg(DEFAULT);
        }
        return literal(ansi.toString());
    }

    @Override
    public BackgroundDefault copy() {
        return new BackgroundDefault(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ConsoleVocabulary.backgroundDefault.name();
    }
}
