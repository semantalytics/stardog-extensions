package com.semantalytics.stardog.kibble.string.phonetic;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;

public final class ColognePhonetic extends AbstractFunction implements StringFunction {

    private static final org.apache.commons.codec.language.ColognePhonetic colognePhonetic;

    static {
        colognePhonetic = new org.apache.commons.codec.language.ColognePhonetic();
    }

    protected ColognePhonetic() {
        super(1, PhoneticVocabulary.colognePhonetic.toString());
    }

    private ColognePhonetic(final ColognePhonetic colognePhonetic) {
        super(colognePhonetic);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        if(assertStringLiteral(values[0])) {

            final String string = ((Literal)values[0]).label();
            return ValueOrError.General.of(literal(colognePhonetic.encode(string)));
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
        return new ColognePhonetic(this);
    }

    @Override
    public String toString() {
        return PhoneticVocabulary.colognePhonetic.toString();
    }
}
