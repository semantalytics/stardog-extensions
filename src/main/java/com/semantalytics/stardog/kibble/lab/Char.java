package com.semantalytics.stardog.lab.function;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;

public class Char extends AbstractFunction implements UserDefinedFunction{

    public Char() {
        super(1, "http://semantalytics.com/2016/03/ns/stardog/udf/util/char");
    }

    public Char(final Char character) {
        super(character);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final int i = assertNumericLiteral(values[0]).intValue();

        return literal(String.valueOf(Character.toChars(i)));
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
