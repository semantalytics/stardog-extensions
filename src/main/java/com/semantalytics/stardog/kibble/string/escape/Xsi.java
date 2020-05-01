package com.semantalytics.stardog.kibble.string.escape;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.text.StringEscapeUtils.*;

public final class Xsi extends AbstractFunction implements StringFunction {

    protected Xsi() {
        super(1, EscapeVocabulary.xsi.stringValue());
    }

    private Xsi(final Xsi xsi) {
        super(xsi);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String string = ((Literal)values[0]).label();

            return ValueOrError.General.of(literal(escapeXSI(string)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Xsi copy() {
        return new Xsi(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return EscapeVocabulary.csv.name();
    }
}
