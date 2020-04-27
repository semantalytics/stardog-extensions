package com.semantalytics.stardog.kibble.net.internetaddress;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;

public class IsValid extends AbstractFunction implements UserDefinedFunction {

    public IsValid() {
        super(1, InternetAddressVocabulary.isValid.stringValue());
    }

    private IsValid(final IsValid isValid) {
        super(isValid);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String ip = assertStringLiteral(values[0]).stringValue();

        return literal(InetAddressValidator.getInstance().isValid(ip));
    }

    @Override
    public Function copy() {
        return new IsValid(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetAddressVocabulary.isValid.name();
    }

}
