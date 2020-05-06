package com.semantalytics.stardog.kibble.net.internetaddress;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.google.common.net.InetAddresses.*;
import static com.stardog.stark.Values.literal;

public class IsIp4MappedAddress extends AbstractFunction implements UserDefinedFunction {

    public IsIp4MappedAddress() {
        super(1, InternetAddressVocabulary.isIp4MappedAddress.stringValue());
    }

    private IsIp4MappedAddress(final IsIp4MappedAddress internetAddressToNumber) {
        super(internetAddressToNumber);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String ip = ((Literal)values[0]).label();

            return ValueOrError.General.of(literal(isMappedIPv4Address(ip)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Function copy() {
        return new IsIp4MappedAddress(this);
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
