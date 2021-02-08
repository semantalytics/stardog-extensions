package com.semantalytics.stardog.kibble.console;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Value;
import org.fusesource.jansi.Ansi;

import static com.stardog.stark.Values.literal;
import static org.fusesource.jansi.Ansi.Color;
import static org.fusesource.jansi.Ansi.ansi;

public class ForegroundDefault extends AbstractFunction implements UserDefinedFunction {

    public ForegroundDefault() {
        super(0, ConsoleVocabulary.foregroundDefault.toString());
    }

    public ForegroundDefault(final ForegroundDefault foreground) {
        super(foreground);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        final Ansi ansi = ansi();
        ansi.fg(Color.DEFAULT);
        return ValueOrError.General.of(literal(ansi.toString()));
    }

    @Override
    public ForegroundDefault copy() {
        return new ForegroundDefault(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ConsoleVocabulary.foregroundDefault.toString();
    }
}
