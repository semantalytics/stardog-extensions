package com.semantalytics.stardog.kibble.net.internetaddress;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.net.InetAddresses;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;
import static com.google.common.net.InetAddresses.*;

public class HasEmbeddedIp4ClientAddress extends AbstractFunction implements UserDefinedFunction {

    public HasEmbeddedIp4ClientAddress() {
        super(1, InternetAddressVocabulary.isIp4MappedAddress.stringValue());
    }

    private HasEmbeddedIp4ClientAddress(final HasEmbeddedIp4ClientAddress internetAddressToNumber) {
        super(internetAddressToNumber);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String ip = assertStringLiteral(values[0]).stringValue();

        return literal(isMappedIPv4Address(ip));
    }

    @Override
    public Function copy() {
        return new HasEmbeddedIp4ClientAddress(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetAddressVocabulary.isIp4MappedAddress.name();
    }

}
