package com.semantalytics.stardog.kibble.string.unescape;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;
import static org.apache.commons.text.StringEscapeUtils.escapeXSI;
import static org.apache.commons.text.StringEscapeUtils.unescapeXSI;

public final class Xsi extends AbstractFunction implements StringFunction {

    protected Xsi() {
        super(1, UnescapeVocabulary.xsi.stringValue());
    }

    private Xsi(final Xsi xsi) {
        super(xsi);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String string = assertStringLiteral(values[0]).stringValue();

        return literal(unescapeXSI(string));
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
        return UnescapeVocabulary.csv.name();
    }
}
