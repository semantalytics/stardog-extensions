package com.semantalytics.stardog.kibble.util;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.ibm.icu.text.RuleBasedNumberFormat;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.util.Locale;

import static com.stardog.stark.Values.literal;

public class SayOrdinal extends AbstractFunction implements UserDefinedFunction {

    public SayOrdinal() {
        super(1, UtilVocabulary.sayOrdinal.stringValue());
    }

    private SayOrdinal(final SayOrdinal sayOrdinal) {
        super(sayOrdinal);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        if(assertNumericLiteral(values[0])) {
            int number = Literal.intValue((Literal)values[0]);

            final String ordinal = new RuleBasedNumberFormat(Locale.US, RuleBasedNumberFormat.ORDINAL).format(number);

            return ValueOrError.General.of(literal(ordinal));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public SayOrdinal copy() {
        return new SayOrdinal(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return "Convert ordinal integer into spoken equivalent";
    }
}
