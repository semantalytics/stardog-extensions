package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class IsEmpty extends AbstractFunction implements StringFunction {

    protected IsEmpty() {
        super(1, StringVocabulary.isEmpty.toString());
    }

    private IsEmpty(final IsEmpty isEmpty) {
        super(isEmpty);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
      
        if(!assertStringLiteral(values[0])) {
            return ValueOrError.Error;
        }

        final String string = ((Literal)values[0]).label();

      return ValueOrError.General.of(literal(isEmpty(string)));
    }

    @Override
    public IsEmpty copy() {
        return new IsEmpty(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.isEmpty.toString();
    }
}
