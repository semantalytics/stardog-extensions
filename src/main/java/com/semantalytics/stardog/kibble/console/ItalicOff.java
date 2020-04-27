package com.semantalytics.stardog.kibble.console;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.fusesource.jansi.Ansi;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;
import static org.fusesource.jansi.Ansi.Color;
import static org.fusesource.jansi.Ansi.ansi;

public class ItalicOff extends AbstractFunction implements UserDefinedFunction {

    public ItalicOff() {
        super(0, ConsoleVocabulary.italicOff.stringValue());
    }

    public ItalicOff(final ItalicOff italicOff) {
        super(italicOff);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
        return literal(ansi().a(Ansi.Attribute.ITALIC_OFF).toString());
    }

    @Override
    public ItalicOff copy() {
        return new ItalicOff(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ConsoleVocabulary.italicOff.name();
    }
}
