package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.apache.commons.lang3.StringUtils;

import static com.stardog.stark.Values.literal;

public final class LastOrdinalIndexOf extends AbstractFunction implements StringFunction {

    protected LastOrdinalIndexOf() {
        super(3, StringVocabulary.lastOrdinalIndexOf.toString());
    }

    private LastOrdinalIndexOf(final LastOrdinalIndexOf lastOrdinalIndexOf) {
        super(lastOrdinalIndexOf);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(!assertStringLiteral(values[0]) || !assertStringLiteral(values[1]) || !assertNumericLiteral(values[2])) {
            return ValueOrError.Error;
        }

        final String string = ((Literal)values[0]).label();
        final String searchString = ((Literal)values[1]).label();
        final int ordinal = Literal.intValue((Literal)values[2]);

        return ValueOrError.General.of(literal(StringUtils.lastOrdinalIndexOf(string, searchString, ordinal)));
    }

    @Override
    public LastOrdinalIndexOf copy() {
        return new LastOrdinalIndexOf(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.lastOrdinalIndexOf.name();
    }
}
