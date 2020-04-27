package com.semantalytics.stardog.kibble.util;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Value;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

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
    protected ValueOrError internalEvaluate(Value... values) {
        
        final String time = assertStringLiteral(values[0]).stringValue();
        final List<Date> dates = parser.parse(time);
        
        if(dates.isEmpty() || dates.size() > 1) {
            throw new ExpressionEvaluationException("Only a single date can be returned. Found " + dates.size());
        }
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(dates.get(0).getTime());
            return literal(calendar);
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
