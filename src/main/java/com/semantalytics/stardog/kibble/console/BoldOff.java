package com.semantalytics.stardog.kibble.console;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.fusesource.jansi.Ansi.ansi;

public class BoldOff extends AbstractFunction implements UserDefinedFunction {

    public BoldOff() {
        super(0, ConsoleVocabulary.boldOff.toString());
    }

    public BoldOff(final BoldOff boldOn) {
        super(boldOn);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        return ValueOrError.General.of(literal(ansi().boldOff().toString()));
    }

    @Override
    public BoldOff copy() {
        return new BoldOff(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ConsoleVocabulary.boldOff.toString();
    }
}
