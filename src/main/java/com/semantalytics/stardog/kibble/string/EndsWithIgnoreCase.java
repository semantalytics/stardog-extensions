package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.apache.commons.lang3.StringUtils;

import static com.stardog.stark.Values.literal;

public final class EndsWithIgnoreCase extends AbstractFunction implements StringFunction {

    protected EndsWithIgnoreCase() {
        super(2, StringVocabulary.endsWithIgnoreCase.toString());
    }

    private EndsWithIgnoreCase(final EndsWithIgnoreCase endsWithIgnoreCase) {
        super(endsWithIgnoreCase);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(!assertStringLiteral(values[0]) || !assertStringLiteral(values[1])) {
            return ValueOrError.Error;
        }

        final String string = ((Literal)values[0]).label();
        final String suffix = ((Literal)values[1]).label();

        return ValueOrError.General.of(literal(StringUtils.endsWithIgnoreCase(string, suffix)));
    }

    @Override
    public EndsWithIgnoreCase copy() {
        return new EndsWithIgnoreCase(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.endsWithIgnoreCase.toString();
    }
}
