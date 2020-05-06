package com.semantalytics.stardog.kibble.util;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;

import static org.apache.sis.internal.jaxb.XmlUtilities.toDate;

public class Proquint extends AbstractFunction implements UserDefinedFunction {

    public Proquint() {
        super(2, UtilVocabulary.proquint.stringValue());
    }

    private Proquint(final Proquint proquint) {
        super(proquint);
    }

    @Override
    protected ValueOrError internalEvaluate(Value... values) {

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {
            final String time = Literal.str((Literal)values[0]);
            final String pattern = Literal.str((Literal)values[1]);

            final GregorianCalendar calendar = new GregorianCalendar();
            final XMLGregorianCalendar date;
            final DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
            calendar.setTime(java.sql.Date.valueOf(LocalDate.parse(time, format)));
            try {
                date = DatatypeFactory.newInstance().newXMLGregorianCalendar();
            } catch (DatatypeConfigurationException e) {
                return ValueOrError.Error;
            }
            return ValueOrError.Calendar.of(date);
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Proquint copy() {
        return new Proquint(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return UtilVocabulary.proquint.name();
    }
}
