package com.semantalytics.stardog.kibble.visualization.ascii;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;

public final class QRCode extends AbstractFunction implements StringFunction {

    public QRCode() {
        super(1, AsciiVisualizationVocabulary.qrcode.stringValue());
    }

    private QRCode(final QRCode qrcode) {
        super(qrcode);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String string = assertStringLiteral(values[0]).stringValue();

        return literal(net.glxn.qrgen.javase.QRCode.from(string).toString());
    }

    public Value evaluate(final Value... values) throws ExpressionEvaluationException {
        this.assertRequiredArgs(values.length);
        return this.internalEvaluate(values);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public QRCode copy() {
        return new QRCode(this);
    }

    @Override
    public String toString() {
        return AsciiVisualizationVocabulary.qrcode.name();
    }

}
