package com.semantalytics.stardog.kibble.date;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.GregorianCalendar;
import java.util.Locale;

import static com.stardog.stark.Values.literal;

public class ParseDate extends AbstractFunction implements UserDefinedFunction {

    public ParseDate() {
        super(Range.closed(2, 3), DateVocabulary.parseDate.stringValue());
    }

    public ParseDate(final ParseDate parseDate) {
        super(parseDate);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {
            final String string = ((Literal)values[0]).label();
            final String pattern = ((Literal)values[1]).label();
            final DateTimeFormatter formatter;

            switch (values.length) {

                case 2: {
                    formatter = DateTimeFormatter.ofPattern(pattern);
                    break;
                }
                case 3: {
                    if(assertStringLiteral(values[2])) {
                        //TODO check for no lang
                        Locale locale = Locale.forLanguageTag(((Literal)values[2]).lang().get());
                        formatter = DateTimeFormatter.ofPattern(pattern, locale);
                    } else {
                        return ValueOrError.Error;
                    }
                    break;

                }
                default:
                    return ValueOrError.Error;
            }

            final ZonedDateTime zonedDateTime;

            try {
                zonedDateTime = ZonedDateTime.parse(string, formatter);
            } catch (DateTimeParseException e) {
                return ValueOrError.Error;
            }

            return ValueOrError.General.of(literal(GregorianCalendar.from(zonedDateTime)));
        } else {
            return ValueOrError.Error;
        }
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