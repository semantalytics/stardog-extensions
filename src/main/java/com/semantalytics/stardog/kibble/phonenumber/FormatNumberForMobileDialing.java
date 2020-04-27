package com.semantalytics.stardog.kibble.phonenumber;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

public final class FormatNumberForMobileDialing extends AbstractFunction implements UserDefinedFunction {

    protected FormatNumberForMobileDialing() {
        super(2, PhoneNumberVocabulary.formatNumberForMobileDialing.stringValue());
    }

    private FormatNumberForMobileDialing(final FormatNumberForMobileDialing formatNumberForMobileDialing) {
        super(formatNumberForMobileDialing);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
      
        return null;
    }

    @Override
    public FormatNumberForMobileDialing copy() {
        return new FormatNumberForMobileDialing(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return PhoneNumberVocabulary.formatNumberForMobileDialing.name();
    }
}
