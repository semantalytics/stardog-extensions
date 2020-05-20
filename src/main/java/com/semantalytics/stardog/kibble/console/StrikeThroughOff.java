package com.semantalytics.stardog.kibble.console;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.fusesource.jansi.Ansi.Attribute.STRIKETHROUGH_OFF;
import static org.fusesource.jansi.Ansi.ansi;

public class StrikeThroughOff extends AbstractFunction implements UserDefinedFunction {

    public StrikeThroughOff() {
        super(0, ConsoleVocabulary.strikeThroughOff.stringValue());
    }

    public StrikeThroughOff(final StrikeThroughOff console) {
        super(console);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        return ValueOrError.General.of(literal(ansi().a(STRIKETHROUGH_OFF).toString()));
    }

    @Override
    public StrikeThroughOff copy() {
        return new StrikeThroughOff(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ConsoleVocabulary.strikeThroughOff.name();
    }
}
