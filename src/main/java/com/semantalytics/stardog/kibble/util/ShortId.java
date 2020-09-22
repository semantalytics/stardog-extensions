package com.semantalytics.stardog.kibble.util;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.aventrix.jnanoid.jnanoid.NanoIdUtils.*;
import static com.stardog.stark.Values.*;

public class ShortId extends AbstractFunction implements UserDefinedFunction {

    public ShortId() {
        super(2, UtilsVocabulary.shortId.toString());
    }

    public ShortId(final ShortId shortId) {
        super(shortId);
    }

    @Override
    protected ValueOrError internalEvaluate(Value... values) {
        switch(values.length) {
            case 0: {
                return ValueOrError.General.of(literal(randomNanoId()));
            }
            case 2: {
                if(assertLiteral(values[0]) && assertIntegerLiteral(values[1])) {
                    final char[] alphabet = ((Literal)values[0]).label().toCharArray();
                    final int size = Literal.intValue((Literal)values[1]);
                    return ValueOrError.General.of(literal(randomNanoId(DEFAULT_NUMBER_GENERATOR, alphabet, size)));
                }
            }
            default:
                return ValueOrError.Error;
        }
    }

    @Override
    public Function copy() {
        return new ShortId(this);
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}