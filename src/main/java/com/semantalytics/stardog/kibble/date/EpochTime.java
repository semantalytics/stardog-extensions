package com.semantalytics.stardog.kibble.date;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Datatype;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import javax.xml.datatype.XMLGregorianCalendar;

import static com.stardog.stark.Values.*;

public class EpochTime extends AbstractFunction implements UserDefinedFunction {

        public EpochTime() {
            super(1, DateVocabulary.epochTime.stringValue());
        }

        public EpochTime(final EpochTime epochTime) {
            super(epochTime);
        }

        @Override
        protected ValueOrError internalEvaluate(final Value... values) {

            if(assertTypedLiteral(values[0], Datatype.DATE)) {
                final XMLGregorianCalendar calendar = Literal.calendarValue((Literal)values[0]);

                return ValueOrError.General.of(literal(calendar.toGregorianCalendar().getTimeInMillis()));
            } else {
                return ValueOrError.Error;
            }
        }

        @Override
        public EpochTime copy() {
            return new EpochTime(this);
        }

        @Override
        public void accept(final ExpressionVisitor expressionVisitor) {
            expressionVisitor.visit(this);
        }

        @Override
        public String toString() {
            return DateVocabulary.epochTime.name();
        }
}
