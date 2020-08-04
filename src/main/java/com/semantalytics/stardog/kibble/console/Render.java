package com.semantalytics.stardog.kibble.console;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.fusesource.jansi.Ansi.ansi;

public class Render extends AbstractFunction implements UserDefinedFunction {

    public Render() {
        super(1, ConsoleVocabulary.render.toString());
    }

    public Render(final Render render) {
        super(render);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        if(assertStringLiteral(values[0])) {
            final String text = ((Literal)values[0]).label();
            return ValueOrError.General.of(literal(ansi().render(text).toString()));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Render copy() {
        return new Render(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ConsoleVocabulary.render.toString();
    }
}
