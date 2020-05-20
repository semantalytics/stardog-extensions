package com.semantalytics.stardog.kibble.string.phonetic;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;

public final class Nysiis extends AbstractFunction implements StringFunction {

    private static final org.apache.commons.codec.language.Nysiis nysiis;

    static {
        nysiis = new org.apache.commons.codec.language.Nysiis();
    }

    protected Nysiis() {
        super(1, PhoneticVocabulary.nysiis.stringValue());
    }

    private Nysiis(final Nysiis caverphone2) {
        super(caverphone2);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        if(assertStringLiteral(values[0])) {
            final String string = ((Literal)values[0]).label();
            return ValueOrError.General.of(literal(nysiis.encode(string)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public Function copy() {
        return new Nysiis(this);
    }

    @Override
    public String toString() {
        return PhoneticVocabulary.nysiis.name();
    }
}
