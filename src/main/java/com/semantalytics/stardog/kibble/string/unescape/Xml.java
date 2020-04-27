package com.semantalytics.stardog.kibble.string.unescape;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;
import static org.apache.commons.text.StringEscapeUtils.unescapeXml;

public final class Xml extends AbstractFunction implements StringFunction {

    protected Xml() {
        super(1, new String[] {UnescapeVocabulary.xml10.stringValue(),
                                             UnescapeVocabulary.xml11.stringValue(),
                                             UnescapeVocabulary.xml.stringValue()});
    }

    private Xml(final Xml xml) {
        super(xml);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String string = assertStringLiteral(values[0]).stringValue();

        return literal(unescapeXml(string));
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
        return UnescapeVocabulary.csv.name();
    }
}
