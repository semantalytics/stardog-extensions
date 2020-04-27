package com.semantalytics.stardog.kibble.util;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Range;
import org.openrdf.model.Value;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

import static com.complexible.common.rdf.model.Values.literal;

public class SayTime extends AbstractFunction implements UserDefinedFunction {

    public SayTime() {
        super(Range.closed(1, 2), UtilVocabulary.sayTime.stringValue());
    }

    private SayTime(final SayTime sayTime) {
        super(sayTime);
    }

    @Override
    protected Value internalEvaluate(Value... values) throws ExpressionEvaluationException {

        final Date from = new Date(assertLiteral(values[0]).calendarValue().getMillisecond());
        final Date date = new Date(assertLiteral(values[1]).calendarValue().getMillisecond());

        final PrettyTime prettyTime = new PrettyTime(from);

        return literal(prettyTime.format(date));
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
