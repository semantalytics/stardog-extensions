package com.semantalytics.stardog.kibble.string.phonetic;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;

public class DoubleMetaphone extends AbstractFunction implements StringFunction {

    private static final org.apache.commons.codec.language.DoubleMetaphone doubleMetaphone;

    static {
        doubleMetaphone = new org.apache.commons.codec.language.DoubleMetaphone();
    }

    protected DoubleMetaphone() {
        super(1, PhoneticVocabulary.doubleMetaphone.toString());
    }

    private DoubleMetaphone(DoubleMetaphone doubleMetaphone) {
        super(doubleMetaphone);
    }

    @Override
    public Function copy() {
        return new DoubleMetaphone(this);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {
        if(assertStringLiteral(values[0])) {

        final String string = ((Literal)values[0]).label();

        return ValueOrError.General.of(literal(doubleMetaphone.doubleMetaphone(string)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneticVocabulary.doubleMetaphone.toString();
    }
}
