package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;


public class UpperCase extends AbstractFunction implements UserDefinedFunction {

    public UpperCase() {
        super(1, StringVocabulary.upperCase.toString());
    }

    public UpperCase(final UpperCase upperCase) {
        super(upperCase);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {

            final String string = ((Literal)values[0]).label();

            return ValueOrError.General.of(literal(upperCase((string))));

        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public UpperCase copy() {
        return new UpperCase(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.upperCase.toString();
    }
}
