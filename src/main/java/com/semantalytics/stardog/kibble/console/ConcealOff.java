package com.semantalytics.stardog.kibble.console;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Value;
import org.fusesource.jansi.Ansi;

import static com.stardog.stark.Values.literal;
import static org.fusesource.jansi.Ansi.ansi;

public class ConcealOff extends AbstractFunction implements UserDefinedFunction {

    public ConcealOff() {
        super(0, ConsoleVocabulary.concealOff.toString());
    }

    public ConcealOff(final ConcealOff conceal) {
        super(conceal);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        return ValueOrError.General.of(literal(ansi().a(Ansi.Attribute.CONCEAL_OFF).toString()));
    }

    @Override
    public ConcealOff copy() {
        return new ConcealOff(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ConsoleVocabulary.concealOff.toString();
    }
}
