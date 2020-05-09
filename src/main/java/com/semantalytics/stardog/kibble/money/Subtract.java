package com.semantalytics.stardog.kibble.money;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import javax.xml.datatype.XMLGregorianCalendar;

import static com.stardog.stark.Values.literal;

public class Subtract extends AbstractFunction implements UserDefinedFunction {

        public Subtract() {
            super(1, MoneyVocabulary.subtract.stringValue());
        }

        public Subtract(final Subtract subtract) {
            super(subtract);
        }

        @Override
        protected ValueOrError internalEvaluate(final Value... values) {

            if(assertLiteral(values[0])) {
                final XMLGregorianCalendar calendar = Literal.calendarValue((Literal)values[0]);

                return ValueOrError.Calendar.of(literal(calendar.toGregorianCalendar().getTimeInMillis()));
            } else {
                return ValueOrError.Error;
            }
        }

        @Override
        public Subtract copy() {
            return new Subtract(this);
        }

        @Override
        public void accept(final ExpressionVisitor expressionVisitor) {
            expressionVisitor.visit(this);
        }

        @Override
        public String toString() {
            return MoneyVocabulary.subtract.name();
        }
}
