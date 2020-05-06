package com.semantalytics.stardog.kibble.date;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Value;
import org.threeten.extra.Quarter;

import static com.stardog.stark.Values.literal;

public class PreviousQuarter extends AbstractFunction implements UserDefinedFunction {

    public PreviousQuarter() {
        super(1, DateVocabulary.previousQuarter.stringValue());
    }

    public PreviousQuarter(final PreviousQuarter previousQuarter) {
        super(previousQuarter);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        final Quarter quarter = org.threeten.extra.Quarter.from(assertLiteral(values[0]).calendarValue().toGregorianCalendar().toZonedDateTime().toLocalDate());

        return ValueOrError.General.of(literal(quarter.minus(1).getValue()));
    }

    @Override
    public PreviousQuarter copy() {
        return new PreviousQuarter(this);
     }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
                                                                expressionVisitor.visit(this);
                                                                                               }

    @Override
    public String toString() {
        return DateVocabulary.previousQuarter.name();
    }
}
