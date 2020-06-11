package com.semantalytics.stardog.kibble.console;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.fusesource.jansi.Ansi;

import java.util.stream.Stream;

import static com.stardog.stark.Values.literal;
import static org.fusesource.jansi.Ansi.Color;
import static org.fusesource.jansi.Ansi.ansi;

public class ForegroundCyan extends AbstractFunction implements UserDefinedFunction {

    public ForegroundCyan() {
        super(Range.all(), ConsoleVocabulary.foregroundCyan.stringValue());
    }

    public ForegroundCyan(final ForegroundCyan foreground) {
        super(foreground);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        final Ansi ansi = ansi();
        ansi.bg(Color.CYAN);

        Stream.of(values).map(v -> assertLiteral(v) ? ((Literal)v).label() : v.toString()).forEach(s -> ansi.a(s));

        if(values.length != 0) {
            ansi.bg(Color.DEFAULT);
        }
        return ValueOrError.General.of(literal(ansi.toString()));
    }

    @Override
    public ForegroundCyan copy() {
        return new ForegroundCyan(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ConsoleVocabulary.backgroundCyan.name();
    }
}
