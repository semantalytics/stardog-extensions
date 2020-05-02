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
import static org.fusesource.jansi.Ansi.ansi;

public class Bold extends AbstractFunction implements UserDefinedFunction {

    public Bold() {
        super(Range.all(), ConsoleVocabulary.bold.stringValue());
    }

    public Bold(final Bold boldOn) {
        super(boldOn);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        final Ansi ansi = ansi();
        ansi.bold();

        Stream.of(values).forEach(v -> ansi.a(v.toString()));

        if(values.length != 0) {
            ansi.boldOff();
        }
        return ValueOrError.General.of(literal(ansi.toString()));
    }

    @Override
    public Bold copy() {
        return new Bold(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ConsoleVocabulary.bold.name();
    }
}
