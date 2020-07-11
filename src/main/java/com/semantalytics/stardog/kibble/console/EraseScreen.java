package com.semantalytics.stardog.kibble.console;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.fusesource.jansi.Ansi.ansi;

public class EraseScreen extends AbstractFunction implements UserDefinedFunction {

    public EraseScreen() {
        super(Range.all(), ConsoleVocabulary.eraseScreen.toString());
    }

    public EraseScreen(final EraseScreen eraseScreen) {
        super(eraseScreen);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        return ValueOrError.General.of(literal(ansi().eraseScreen().toString()));
    }

    @Override
    public EraseScreen copy() {
        return new EraseScreen(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ConsoleVocabulary.eraseScreen.toString();
    }
}
