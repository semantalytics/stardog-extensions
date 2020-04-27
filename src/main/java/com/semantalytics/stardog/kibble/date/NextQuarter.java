package com.semantalytics.stardog.kibble.date;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Datatype;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.threeten.extra.Quarter;

public class NextQuarter extends AbstractFunction implements UserDefinedFunction {

    public NextQuarter() {
        super(1, DateVocabulary.nextQuarter.stringValue());
    }

    public NextQuarter(final NextQuarter nextQuarter) {
        super(nextQuarter);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertTypedLiteral(values[0], Datatype.DATE) || assertTypedLiteral(values[0], Datatype.DATETIME) || assertTypedLiteral(values[0], Datatype.DATETIMESTAMP)) {

            final Quarter quarter = org.threeten.extra.Quarter.from(Literal.calendarValue(((Literal)values[0])).toGregorianCalendar().toZonedDateTime().toLocalDate());

            return ValueOrError.Int.of(quarter.plus(1).getValue());
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public NextQuarter copy() {
                            return new NextQuarter(this);
                                                         }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
                                                                expressionVisitor.visit(this);
                                                                                               }

    @Override
    public String toString() {
        return DateVocabulary.nextQuarter.name();
    }
}
