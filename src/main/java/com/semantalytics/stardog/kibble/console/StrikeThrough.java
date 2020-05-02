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
import static org.fusesource.jansi.Ansi.Attribute.*;
import static org.fusesource.jansi.Ansi.ansi;

public class StrikeThrough extends AbstractFunction implements UserDefinedFunction {

    public StrikeThrough() {
        super(Range.all(), ConsoleVocabulary.strikeThrough.toString());
    }

    public StrikeThrough(final StrikeThrough console) {
        super(console);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        final Ansi ansi = ansi();
        ansi.a(STRIKETHROUGH_ON);

        Stream.of(values).forEach(v -> ansi.a(v.toString()));

        if(values.length != 0) {
            ansi.a(STRIKETHROUGH_OFF);
        }
        return ValueOrError.General.of(literal(ansi.toString()));
    }

    @Override
    public StrikeThrough copy() {
        return new StrikeThrough(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ConsoleVocabulary.strikeThrough.name();
    }
}
