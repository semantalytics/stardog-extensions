package com.semantalytics.stardog.kibble.console;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Value;
import org.fusesource.jansi.Ansi;

import java.util.stream.Stream;

import static com.stardog.stark.Values.literal;
import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

public class BackgroundRed extends AbstractFunction implements UserDefinedFunction {

    public BackgroundRed() {
        super(Range.all(), ConsoleVocabulary.backgroundRed.stringValue());
    }

    public BackgroundRed(final BackgroundRed console) {
        super(console);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        final Ansi ansi = ansi();
        ansi.bg(RED);

        Stream.of(values).forEach(v -> ansi.a(v.toString()));

        if(values.length != 0) {
            ansi.bg(DEFAULT);
        }
        return ValueOrError.General.of(literal(ansi.toString()));
    }

    @Override
    public BackgroundRed copy() {
        return new BackgroundRed(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ConsoleVocabulary.backgroundRed.name();
    }
}
