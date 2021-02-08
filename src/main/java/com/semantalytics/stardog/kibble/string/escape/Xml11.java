package com.semantalytics.stardog.kibble.string.escape;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.text.StringEscapeUtils.*;

public final class Xml11 extends AbstractFunction implements StringFunction {

    protected Xml11() {
        super(1, EscapeVocabulary.xml11.toString());
    }

    private Xml11(final Xml11 xml11) {
        super(xml11);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String string = ((Literal)values[0]).label();

            return ValueOrError.General.of(literal(escapeXml11(string)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Xml11 copy() {
        return new Xml11(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return EscapeVocabulary.csv.toString();
    }
}
