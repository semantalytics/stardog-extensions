package com.semantalytics.stardog.kibble.util;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Not extends AbstractFunction implements UserDefinedFunction {

    public Not() {
        super(1, UtilVocabulary.not.stringValue());
    }

    private Not(final Not dateTimeFormat) {
        super(dateTimeFormat);
    }

    @Override
    protected ValueOrError internalEvaluate(Value... values) {

        if(assertLiteral(values[0])) {
            return ValueOrError.Boolean.of(!Literal.booleanValue((Literal)values[0]));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Not copy() {
        return new Not(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return UtilVocabulary.not.name();
    }
}
