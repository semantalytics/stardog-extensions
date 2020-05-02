package com.semantalytics.stardog.kibble.console;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.fusesource.jansi.Ansi.Attribute.*;
import static org.fusesource.jansi.Ansi.ansi;

public class UnderlineOff extends AbstractFunction implements UserDefinedFunction {

    public UnderlineOff() {
        super(0, ConsoleVocabulary.underlineOff.stringValue());
    }

    public UnderlineOff(final UnderlineOff console) {
        super(console);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        return ValueOrError.General.of(literal(ansi().a(UNDERLINE_OFF).toString()));
    }

    @Override
    public UnderlineOff copy() {
        return new UnderlineOff(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ConsoleVocabulary.underlineOff.name();
    }
}
