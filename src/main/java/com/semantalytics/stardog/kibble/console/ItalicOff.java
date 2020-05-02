package com.semantalytics.stardog.kibble.console;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Value;
import org.fusesource.jansi.Ansi;

import static com.stardog.stark.Values.literal;
import static org.fusesource.jansi.Ansi.ansi;

public class ItalicOff extends AbstractFunction implements UserDefinedFunction {

    public ItalicOff() {
        super(0, ConsoleVocabulary.italicOff.stringValue());
    }

    public ItalicOff(final ItalicOff italicOff) {
        super(italicOff);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        return ValueOrError.General.of(literal(ansi().a(Ansi.Attribute.ITALIC_OFF).toString()));
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
