package com.semantalytics.stardog.kibble.console;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Range;
import org.fusesource.jansi.Ansi;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.ansi;

public class BoldOff extends AbstractFunction implements UserDefinedFunction {

    public BoldOff() {
        super(0, ConsoleVocabulary.boldOff.stringValue());
    }

    public BoldOff(final BoldOff boldOn) {
        super(boldOn);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
        return literal(ansi().boldOff().toString());
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
        return ConsoleVocabulary.boldOff.name();
    }
}
