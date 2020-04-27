package com.semantalytics.stardog.kibble.console;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Range;
import org.fusesource.jansi.Ansi;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;
import static org.fusesource.jansi.Ansi.Attribute.NEGATIVE_OFF;
import static org.fusesource.jansi.Ansi.Attribute.NEGATIVE_ON;
import static org.fusesource.jansi.Ansi.ansi;

public class NegativeOff extends AbstractFunction implements UserDefinedFunction {

    public NegativeOff() {
        super(0, ConsoleVocabulary.negativeOff.stringValue());
    }

    public NegativeOff(final NegativeOff console) {
        super(console);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
        return literal(ansi().a(NEGATIVE_OFF).toString());
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
