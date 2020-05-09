package com.semantalytics.stardog.kibble.string.phonetic;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;

public class DaitchMokotoffSoundex extends AbstractFunction implements StringFunction {

    private static final org.apache.commons.codec.language.DaitchMokotoffSoundex daitchMokotoffSoundex;

    static {
        daitchMokotoffSoundex = new org.apache.commons.codec.language.DaitchMokotoffSoundex();
    }

    protected DaitchMokotoffSoundex() {
        super(1, PhoneticVocabulary.daitchMokotoffSoundex.stringValue());
    }

    private DaitchMokotoffSoundex(final DaitchMokotoffSoundex daitchMokotoffSoundex) {
        super(daitchMokotoffSoundex);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {
        if(assertStringLiteral(values[0])) {
            final String string = ((Literal)values[0]).label();
            return ValueOrError.General.of(literal(daitchMokotoffSoundex.encode(string)));
        } else {
            return ValueOrError.Error;
        }

    }

    @Override
    public Function copy() {
        return new DaitchMokotoffSoundex(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneticVocabulary.daitchMokotoffSoundex.name();
    }
}

