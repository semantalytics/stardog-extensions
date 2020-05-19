package com.semantalytics.stardog.kibble.console;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.fusesource.jansi.Ansi.ansi;

public class EraseLine extends AbstractFunction implements UserDefinedFunction {

    public EraseLine() {
        super(Range.all(), ConsoleVocabulary.eraseLine.stringValue());
    }

    public EraseLine(final EraseLine eraseLine) {
        super(eraseLine);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        return ValueOrError.General.of(literal(ansi().eraseLine().toString()));
    }

    @Override
    public EraseLine copy() {
        return new EraseLine(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ConsoleVocabulary.eraseLine.name();
    }
}
