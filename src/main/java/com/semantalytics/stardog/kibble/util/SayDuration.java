package com.semantalytics.stardog.kibble.util;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.ibm.icu.text.RuleBasedNumberFormat;
import com.stardog.stark.Datatype;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.time.Duration;
import java.util.Locale;

import static com.stardog.stark.Values.literal;

public class SayDuration extends AbstractFunction implements UserDefinedFunction {

    public SayDuration() {
        super(1, UtilVocabulary.sayDuration.stringValue());
    }

    private SayDuration(final SayDuration sayOrdinal) {
        super(sayOrdinal);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        if (assertTypedLiteral(values[0], Datatype.DURATION) || assertTypedLiteral(values[0], Datatype.DURATION_DAYTIME) || assertTypedLiteral(values[0], Datatype.DURATION_YEARMONTH)) {
            Duration.parse(Literal.durationValue(((Literal) values[0])).toString());
        } else if (assertNumericLiteral(values[0])) {
            int number = Literal.intValue((Literal) values[0]);

            //TODO Handle language tag??
            //TODO generic speak using datatype???
            final String ordinal = new RuleBasedNumberFormat(Locale.US, RuleBasedNumberFormat.DURATION).format(number);

            return ValueOrError.General.of(literal(ordinal));
        } else {
            return ValueOrError.Error;
        }
        return null;
    }

    @Override
    public SayDuration copy() {
        return new SayDuration(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return UtilVocabulary.sayDuration.name();
    }
}
