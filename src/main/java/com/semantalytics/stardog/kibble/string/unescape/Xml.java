package com.semantalytics.stardog.kibble.string.unescape;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.text.StringEscapeUtils.unescapeXml;

public final class Xml extends AbstractFunction implements StringFunction {

    protected Xml() {
        super(1, new String[] {UnescapeVocabulary.xml10.toString(),
                                             UnescapeVocabulary.xml11.toString(),
                                             UnescapeVocabulary.xml.toString()});
    }

    private Xml(final Xml xml) {
        super(xml);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String string = ((Literal)values[0]).label();

            return ValueOrError.General.of(literal(unescapeXml(string)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Xml copy() {
        return new Xml(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return UnescapeVocabulary.csv.toString();
    }
}
