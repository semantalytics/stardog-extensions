package com.semantalytics.stardog.lab.function;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;

public class Bin extends AbstractFunction implements UserDefinedFunction {

    public Bin() {
        super(1, "http://semantalytics.com/2016/03/ns/stardog/udf/util/bin");
    }

    public Bin(final Bin bin) {
        super(bin);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final int i = assertNumericLiteral(values[0]).intValue();

        return literal(Integer.toBinaryString(i));
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
