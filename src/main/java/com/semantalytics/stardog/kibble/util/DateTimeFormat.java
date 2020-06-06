package com.semantalytics.stardog.kibble.util;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeFormat extends AbstractFunction implements UserDefinedFunction {

    public DateTimeFormat() {
        super(2, UtilVocabulary.dateTimeFormat.stringValue());
    }

    private DateTimeFormat(final DateTimeFormat dateTimeFormat) {
        super(dateTimeFormat);
    }

    @Override
    protected ValueOrError internalEvaluate(Value... values) {

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {
            final String time = Literal.str((Literal)values[0]);
            final String pattern = Literal.str((Literal)values[1]);

            DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
            // return ValueOrError.Calendar.of(LocalDate.parse(time, format));
            return null;
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public DateTimeFormat copy() {
        return new DateTimeFormat(this);
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
