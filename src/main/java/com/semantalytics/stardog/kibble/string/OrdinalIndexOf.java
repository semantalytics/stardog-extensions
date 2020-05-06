package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class OrdinalIndexOf extends AbstractFunction implements StringFunction {

    protected OrdinalIndexOf() {
        super(3, StringVocabulary.ordinalIndexOf.toString());
    }

    private OrdinalIndexOf(final OrdinalIndexOf ordinalIndexOf) {
        super(ordinalIndexOf);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(!assertStringLiteral(values[0]) || !assertStringLiteral(values[1]) || !assertNumericLiteral(values[2])) {
            return ValueOrError.Error;
        }

        final String string = ((Literal)values[0]).label();
        final String searchStr = ((Literal)values[1]).label();
        final int ordinal = Literal.intValue((Literal)values[2]);

        return ValueOrError.General.of(literal(ordinalIndexOf(string, searchStr, ordinal)));
    }

    @Override
    public OrdinalIndexOf copy() {
        return new OrdinalIndexOf(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.ordinalIndexOf.name();
    }
}
