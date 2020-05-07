package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.apache.commons.lang3.StringUtils;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class Compare extends AbstractFunction implements StringFunction {

    protected Compare() {
        super(2, StringVocabulary.compare.toString());
    }

    private Compare(final Compare compare) {
        super(compare);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(!assertStringLiteral(values[0]) || !assertStringLiteral(values[1])) {
            return ValueOrError.Error;
        }
        final String string1 = ((Literal)(values[0])).label();
        final String string2 = ((Literal)(values[1])).label();

        return ValueOrError.General.of(literal(compare(string1, string2)));
    }

    @Override
    public Compare copy() {
        return new Compare(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.compare.name();
    }
}
