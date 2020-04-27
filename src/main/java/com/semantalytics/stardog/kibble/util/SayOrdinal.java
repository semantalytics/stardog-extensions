package com.semantalytics.stardog.kibble.util;

import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.ibm.icu.text.RuleBasedNumberFormat;
import org.openrdf.model.Value;

import java.util.Locale;

public class SayOrdinal extends AbstractFunction implements UserDefinedFunction {

    public SayOrdinal() {
        super(1, UtilVocabulary.sayOrdinal.stringValue());
    }

    private SayOrdinal(final SayOrdinal sayOrdinal) {
        super(sayOrdinal);
    }

    @Override
    protected Value internalEvaluate(Value... values) throws ExpressionEvaluationException {
        int number = assertNumericLiteral(values[0]).intValue();
        
        String ordinal = new RuleBasedNumberFormat(Locale.US, RuleBasedNumberFormat.ORDINAL).format(number);

        return Values.literal(ordinal);
    }

    @Override
    public SayOrdinal copy() {
        return new SayOrdinal(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return "Convert ordinal integer into spoken equivalent";
    }
}
