package com.semantalytics.stardog.kibble.util;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.util.GregorianCalendar;

import static com.stardog.stark.Values.literal;

public class Sleep extends AbstractFunction implements UserDefinedFunction {

    protected Sleep() {
        super(1, UtilVocabulary.sleep.stringValue());
    }

    public Sleep(final Sleep sleep) {
        super(sleep);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        if(assertNumericLiteral(values[0])) {
            try {
                Thread.sleep(Literal.intValue((Literal)values[0]));
            } catch(InterruptedException e) {
                return ValueOrError.Error;
            }
            return ValueOrError.General.of(literal((GregorianCalendar)GregorianCalendar.getInstance()));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Function copy() {
        return new Sleep(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return "sleep";
    }
}
