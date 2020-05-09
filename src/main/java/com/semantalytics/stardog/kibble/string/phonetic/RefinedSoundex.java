package com.semantalytics.stardog.kibble.string.phonetic;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;

public class RefinedSoundex extends AbstractFunction implements StringFunction {

    private static final org.apache.commons.codec.language.RefinedSoundex refinedSoundex;

    static {
        refinedSoundex = new org.apache.commons.codec.language.RefinedSoundex();
    }

    protected RefinedSoundex() {
        super(1, PhoneticVocabulary.refinedSoundex.stringValue());
    }

    private RefinedSoundex(RefinedSoundex doubleMetaphone) {
        super(doubleMetaphone);
    }

    @Override
    public Function copy() {
        return new RefinedSoundex(this);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {
        if(assertStringLiteral(values[0])) {
            final String string = ((Literal)values[0]).label();

            //TODO check language

            return ValueOrError.General.of(literal(refinedSoundex.soundex(string)));
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
        return PhoneticVocabulary.refinedSoundex.name();
    }
}
