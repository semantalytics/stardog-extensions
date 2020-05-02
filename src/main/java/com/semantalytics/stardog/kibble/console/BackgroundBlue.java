package com.semantalytics.stardog.kibble.console;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.fusesource.jansi.Ansi;

import java.util.Arrays;
import java.util.stream.Stream;

import static com.stardog.stark.Values.literal;
import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

public class BackgroundBlue extends AbstractFunction implements UserDefinedFunction {

    public BackgroundBlue() {
        super(Range.all(), ConsoleVocabulary.backgroundBlue.stringValue());
    }

    public BackgroundBlue(final BackgroundBlue console) {
        super(console);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        if(Arrays.stream(values).allMatch(AbstractFunction::assertLiteral)) {
            final Ansi ansi = ansi();
            ansi.bg(BLUE);

            Stream.of(values).map(Literal.class::cast).forEach(literal -> ansi.a(literal.label()));

            if (values.length != 0) {
                ansi.bg(DEFAULT);
            }
            return ValueOrError.General.of(literal(ansi.toString()));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public BackgroundBlue copy() {
        return new BackgroundBlue(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ConsoleVocabulary.backgroundBlue.name();
    }
}
