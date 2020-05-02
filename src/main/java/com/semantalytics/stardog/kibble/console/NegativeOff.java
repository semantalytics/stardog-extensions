package com.semantalytics.stardog.kibble.console;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.fusesource.jansi.Ansi.Attribute.NEGATIVE_OFF;
import static org.fusesource.jansi.Ansi.ansi;

public class NegativeOff extends AbstractFunction implements UserDefinedFunction {

    public NegativeOff() {
        super(0, ConsoleVocabulary.negativeOff.stringValue());
    }

    public NegativeOff(final NegativeOff console) {
        super(console);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        return ValueOrError.General.of(literal(ansi().a(NEGATIVE_OFF).toString()));
    }

    @Override
    public NegativeOff copy() {
        return new NegativeOff(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ConsoleVocabulary.negativeOff.name();
    }
}
