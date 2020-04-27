package com.semantalytics.stardog.kibble.date;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

import javax.xml.datatype.XMLGregorianCalendar;

import static com.complexible.common.rdf.model.Values.literal;


public class Quarter extends AbstractFunction implements UserDefinedFunction {

    public Quarter() {
        super(1, DateVocabulary.quarter.stringValue());
    }

    public Quarter(final Quarter quarter) {
        super(quarter);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final XMLGregorianCalendar calendar = assertLiteral(values[0]).calendarValue();
        final org.threeten.extra.Quarter quarter;
        quarter = org.threeten.extra.Quarter.from(calendar.toGregorianCalendar().toZonedDateTime().toLocalDate());

        return literal(quarter.getValue());
    }

    @Override
    public Quarter copy() {
        return new Quarter(this);
     }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
                                                                expressionVisitor.visit(this);
                                                                                               }

    @Override
    public String toString() {
        return DateVocabulary.quarter.name();
    }
}
