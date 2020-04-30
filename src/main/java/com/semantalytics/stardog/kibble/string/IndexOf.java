package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public class IndexOf extends AbstractFunction implements StringFunction {

    protected IndexOf() {
        super(Range.closed(2, 3), StringVocabulary.indexOf.toString());
    }

    private IndexOf(final IndexOf indexOf) {
        super(indexOf);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(!assertStringLiteral(values[0]) || !assertStringLiteral(values[1])) {
            return ValueOrError.Error;
        }

        final String sequence = ((Literal)values[0]).label();
        final String searchSequence = ((Literal)values[1]).label();

        switch(values.length) {
            case 2: {
                return ValueOrError.General.of(literal(indexOf(sequence, searchSequence)));
            }
            case 3: {
                if(!assertNumericLiteral(values[2])) {
                    return ValueOrError.Error;
                }
                final int startPosition = Literal.intValue((Literal)values[2]);

                return ValueOrError.General.of(literal(indexOf(sequence, searchSequence, startPosition)));
            }
            default: {
                return ValueOrError.Error;
            }
        }
    }

    @Override
    public IndexOf copy() {
        return new IndexOf(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.indexOf.name();
    }
}
