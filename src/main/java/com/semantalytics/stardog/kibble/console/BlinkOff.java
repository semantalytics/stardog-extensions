package com.semantalytics.stardog.kibble.console;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.fusesource.jansi.Ansi.Attribute.*;
import static org.fusesource.jansi.Ansi.ansi;

public class BlinkOff extends AbstractFunction implements UserDefinedFunction {

    public BlinkOff() {
        super(0, ConsoleVocabulary.blinkOff.toString());
    }

    public BlinkOff(final BlinkOff blinkOff) {
        super(blinkOff);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        return ValueOrError.General.of(literal(ansi().a(BLINK_OFF).toString()));
    }

    @Override
    public BlinkOff copy() {
        return new BlinkOff(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ConsoleVocabulary.blinkOff.toString();
    }
}
