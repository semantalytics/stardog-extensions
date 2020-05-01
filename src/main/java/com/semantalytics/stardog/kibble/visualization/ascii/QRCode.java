package com.semantalytics.stardog.kibble.visualization.ascii;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;

public final class QRCode extends AbstractFunction implements StringFunction {

    public QRCode() {
        super(1, AsciiVisualizationVocabulary.qrcode.stringValue());
    }

    private QRCode(final QRCode qrcode) {
        super(qrcode);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String string = ((Literal)values[0]).label();

            return ValueOrError.General.of(literal(net.glxn.qrgen.javase.QRCode.from(string).toString()));
        } else {
            return ValueOrError.Error;
        }
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
