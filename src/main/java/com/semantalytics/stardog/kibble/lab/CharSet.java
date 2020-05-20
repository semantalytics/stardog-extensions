package com.semantalytics.stardog.kibble.lab;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.apache.tika.parser.txt.CharsetDetector;

import static com.stardog.stark.Values.literal;

public final class CharSet extends AbstractFunction implements StringFunction {

    protected CharSet() {
        super(1, "jfdkjfskfjsadjskaj");
    }

    private CharSet(final CharSet charSet) {
        super(charSet);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if (assertStringLiteral(values[0])) {

            final String string = ((Literal) values[0]).label();

            CharsetDetector detector = new CharsetDetector();
            detector.setText(string.getBytes());
            return ValueOrError.General.of(literal(detector.detect().getName()));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public CharSet copy() {
        return new CharSet(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return "kdjfdkfjds";
    }
}
