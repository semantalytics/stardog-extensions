package com.semantalytics.stardog.kibble.console;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Range;
import org.fusesource.jansi.Ansi;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;
import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

public class EraseLine extends AbstractFunction implements UserDefinedFunction {

    public EraseLine() {
        super(Range.all(), ConsoleVocabulary.eraseLine.stringValue());
    }

    public EraseLine(final EraseLine eraseLine) {
        super(eraseLine);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {        
        return literal(ansi().eraseLine().toString());
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
