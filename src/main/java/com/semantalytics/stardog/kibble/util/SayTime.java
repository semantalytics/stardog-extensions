package com.semantalytics.stardog.kibble.util;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

import static com.stardog.stark.Values.literal;

public class SayTime extends AbstractFunction implements UserDefinedFunction {

    public SayTime() {
        super(Range.closed(1, 2), UtilVocabulary.sayTime.stringValue());
    }

    private SayTime(final SayTime sayTime) {
        super(sayTime);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        //TODO should check datatype
        if(assertLiteral(values[0]) && assertLiteral(values[1])) {
            final Date from = new Date(Literal.calendarValue((Literal)values[0]).getMillisecond());
            final Date date = new Date(Literal.calendarValue((Literal)values[1]).getMillisecond());

            final PrettyTime prettyTime = new PrettyTime(from);

            return ValueOrError.General.of(literal(prettyTime.format(date)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public SayTime copy() {
        return new SayTime(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return "sayTime";
    }
}
