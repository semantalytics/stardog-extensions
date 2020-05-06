package com.semantalytics.stardog.kibble.util;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;

public class Ordinalize extends AbstractFunction implements UserDefinedFunction {

    public Ordinalize() {
        super(1, UtilVocabulary.ordinalize.stringValue());
    }

    private Ordinalize(final Ordinalize ordinalize) {
        super(ordinalize);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        
        if(assertNumericLiteral(values[0])) {
            final int n = Literal.intValue((Literal)values[0]);

            switch (n % 100) {
                case 11:
                case 12:
                case 13:
                    return ValueOrError.General.of(literal(String.valueOf(n) + "th"));
                default:
                    switch (n % 10) {
                        case 1:
                            return ValueOrError.General.of(literal(String.valueOf(n) + "st"));
                        case 2:
                            return ValueOrError.General.of(literal(String.valueOf(n) + "nd"));
                        case 3:
                            return ValueOrError.General.of(literal(String.valueOf(n) + "rd"));
                        default:
                            return ValueOrError.General.of(literal(String.valueOf(n) + "th"));
                    }
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Ordinalize copy() {
        return new Ordinalize(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return UtilVocabulary.ordinalize.name();
    }
}
