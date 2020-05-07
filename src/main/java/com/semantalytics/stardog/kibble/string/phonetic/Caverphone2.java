package com.semantalytics.stardog.kibble.string.phonetic;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;

public final class Caverphone2 extends AbstractFunction implements StringFunction {

    private static final org.apache.commons.codec.language.Caverphone2 caverphone2;

    static {
        caverphone2 = new org.apache.commons.codec.language.Caverphone2();
    }

    protected Caverphone2() {
        super(1, PhoneticVocabulary.carverphone2.stringValue());
    }

    private Caverphone2(final Caverphone2 caverphone2) {
        super(caverphone2);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        if(assertStringLiteral(values[0])) {
            final String string = ((Literal)values[0]).label();
            //TODO Checkj language tag for english. What do for string literal without language tag?
            return ValueOrError.General.of(literal(caverphone2.encode(string)));
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
        return new Caverphone2(this);
    }

    @Override
    public String toString() {
        return PhoneticVocabulary.carverphone2.name();
    }
}
