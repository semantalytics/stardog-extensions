package com.semantalytics.stardog.kibble.lab;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;

public class Bin extends AbstractFunction implements UserDefinedFunction {

    public Bin() {
        super(1, "http://semantalytics.com/2016/03/ns/stardog/udf/util/bin");
    }

    public Bin(final Bin bin) {
        super(bin);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertNumericLiteral(values[0])) {
            final int i = Literal.intValue((Literal)values[0]);

            return ValueOrError.General.of(literal(Integer.toBinaryString(i)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Bin copy() {
        return new Bin(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return "bin";
    }
}
