package com.semantalytics.stardog.kibble.net.internetaddress;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.net.InetAddresses;
import com.google.common.primitives.UnsignedInts;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;

public class InternetAddressToNumber extends AbstractFunction implements UserDefinedFunction {

    public InternetAddressToNumber() {
        super(1, InternetAddressVocabulary.toNumber.stringValue());
    }

    private InternetAddressToNumber(final InternetAddressToNumber internetAddressToNumber) {
        super(internetAddressToNumber);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String ip = assertStringLiteral(values[0]).stringValue();

        return literal(UnsignedInts.toLong(InetAddresses.coerceToInteger(InetAddresses.forString(ip))));
    }

    @Override
    public Function copy() {
        return new InternetAddressToNumber(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetAddressVocabulary.toNumber.name();
    }

}
