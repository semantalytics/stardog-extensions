package com.semantalytics.stardog.kibble.string.phonetic;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.codec.language.Soundex.*;

public final class Soundex extends AbstractFunction implements StringFunction {

    protected Soundex() {
        super(1, PhoneticVocabulary.soundex.toString());
    }

    private Soundex(final Soundex soundex) {
        super(soundex);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String string = ((Literal)values[0]).label();
            //TODO check language
            return ValueOrError.General.of(literal(US_ENGLISH.soundex(string)));
        } else {
            return ValueOrError.Error;
        }
    }

    public Function copy() {
        return new Soundex(this);
    }

    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneticVocabulary.soundex.toString();
    }
}
