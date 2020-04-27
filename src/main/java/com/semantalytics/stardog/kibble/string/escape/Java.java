package com.semantalytics.stardog.kibble.string.escape;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;
import static org.apache.commons.text.StringEscapeUtils.*;

public final class Java extends AbstractFunction implements StringFunction {

    protected Java() {
        super(1, EscapeVocabulary.java.stringValue());
    }

    private Java(final Java java) {
        super(java);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String string = assertStringLiteral(values[0]).stringValue();

        return literal(escapeJava(string));
    }

    @Override
    public Java copy() {
        return new Java(this);
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
