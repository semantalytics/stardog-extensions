package com.semantalytics.stardog.kibble.date;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Range;
import org.openrdf.model.Value;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.GregorianCalendar;
import java.util.Locale;

import static com.complexible.common.rdf.model.Values.literal;

public class ParseDate extends AbstractFunction implements UserDefinedFunction {

    public ParseDate() {
        super(Range.closed(2, 3), DateVocabulary.parseDate.stringValue());
    }

    public ParseDate(final ParseDate parseDate) {
        super(parseDate);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String string = assertStringLiteral(values[0]).stringValue();
        final String pattern = assertStringLiteral(values[1]).stringValue();
        final DateTimeFormatter formatter;

        switch(values.length) {

            case 2: {
                formatter = DateTimeFormatter.ofPattern(pattern);
                break;
            }
            case 3: {
                Locale locale = Locale.forLanguageTag(assertStringLiteral(values[2]).stringValue());
                formatter = DateTimeFormatter.ofPattern(pattern, locale);
                break;
            }
            default:
                throw new ExpressionEvaluationException("Function takes 2 or 3 arguments. Found " + values.length);
        }

        final ZonedDateTime zonedDateTime;

        try {
            zonedDateTime = ZonedDateTime.parse(string, formatter);
        } catch(DateTimeParseException e) {
            throw new ExpressionEvaluationException(e);
        }

        return literal(GregorianCalendar.from(zonedDateTime));
    }

    @Override
    public ParseDate copy() {
        return new ParseDate(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return DateVocabulary.parseDate.name();
    }
}