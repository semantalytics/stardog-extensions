package com.semantalytics.stardog.kibble.util;

import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

public class Ordinalize extends AbstractFunction implements UserDefinedFunction {

    public Ordinalize() {
        super(1, UtilVocabulary.ordinalize.stringValue());
    }

    private Ordinalize(final Ordinalize ordinalize) {
        super(ordinalize);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
        
        final int n = assertNumericLiteral(values[0]).intValue();
        
        switch (n % 100) {
            case 11:
            case 12:
            case 13:
                return Values.literal(String.valueOf(n) + "th");
            default:
                switch (n % 10) {
                    case 1:
                        return Values.literal(String.valueOf(n) + "st");
                    case 2:
                        return Values.literal(String.valueOf(n) + "nd");
                    case 3:
                        return Values.literal(String.valueOf(n) + "rd");
                    default:
                        return Values.literal(String.valueOf(n) + "th");
                }
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
