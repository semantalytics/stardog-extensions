package com.semantalytics.stardog.kibble.lab;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;

public class Char extends AbstractFunction implements UserDefinedFunction{

    public Char() {
        super(1, "http://semantalytics.com/2016/03/ns/stardog/udf/util/char");
    }

    public Char(final Char character) {
        super(character);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertNumericLiteral(values[0])) {
            final int i = Literal.intValue((Literal)values[0]);
            try {
                return ValueOrError.General.of(literal(String.valueOf(Character.toChars(i))));
            } catch(IllegalArgumentException e) {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Char copy() {
        return new Char(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return "char";
    }
}
