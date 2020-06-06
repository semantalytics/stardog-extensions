package com.semantalytics.stardog.kibble.util;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class FromSpokenTime extends AbstractFunction implements UserDefinedFunction {

    private static final PrettyTimeParser parser = new PrettyTimeParser();

    public FromSpokenTime() {
        super(1, UtilVocabulary.fromSpokenTime.stringValue());
    }

    private FromSpokenTime(final FromSpokenTime fromSpokenTime) {
        super(fromSpokenTime);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        //TODO use ArrayLiteral to handle multiple dates
        
        if(assertStringLiteral(values[0])) {
            final String time = ((Literal)values[0]).label();
            final List<Date> dates = parser.parse(time);

            if (dates.isEmpty() || dates.size() > 1) {
                return ValueOrError.Error;
            }
            final GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTimeInMillis(dates.get(0).getTime());
            final XMLGregorianCalendar date;
            try {
                date = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
                return ValueOrError.Calendar.of(date);
            } catch (DatatypeConfigurationException e) {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public FromSpokenTime copy() {
        return new FromSpokenTime(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return UtilVocabulary.fromSpokenTime.name();
    }
}
