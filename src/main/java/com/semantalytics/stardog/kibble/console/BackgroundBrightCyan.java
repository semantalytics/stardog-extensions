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
import static org.fusesource.jansi.Ansi.Color.DEFAULT;
import static org.fusesource.jansi.Ansi.ansi;

public class BackgroundBrightCyan extends AbstractFunction implements UserDefinedFunction {

    public BackgroundBrightCyan() {
        super(Range.all(), ConsoleVocabulary.backgroundBrightCyan.toString());
    }

    public BackgroundBrightCyan(final BackgroundBrightCyan console) {
        super(console);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        final Ansi ansi = ansi();
        ansi.bgBright(Color.CYAN);

        Stream.of(values).map(v -> assertLiteral(v) ? ((Literal)v).label() : v.toString()).forEach(s -> ansi.a(s));

        if(values.length != 0) {
            ansi.bgBright(DEFAULT);
        }
        return ValueOrError.General.of(literal(ansi.toString()));
    }

    @Override
    public BackgroundBrightCyan copy() {
        return new BackgroundBrightCyan(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ConsoleVocabulary.backgroundBrightCyan.toString();
    }
}
