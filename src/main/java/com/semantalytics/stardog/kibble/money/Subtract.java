package com.semantalytics.stardog.kibble.money;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.semantalytics.stardog.kibble.money.MoneyVocabulary;
import org.openrdf.model.Value;

import javax.xml.datatype.XMLGregorianCalendar;

import static com.complexible.common.rdf.model.Values.literal;

public class Subtract extends AbstractFunction implements UserDefinedFunction {

        public Subtract() {
            super(1, MoneyVocabulary.subtract.stringValue());
        }

        public Subtract(final Subtract subtract) {
            super(subtract);
        }

        @Override
        protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {


            final XMLGregorianCalendar calendar = assertLiteral(values[0]).calendarValue();

            return literal(calendar.toGregorianCalendar().getTimeInMillis());
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
