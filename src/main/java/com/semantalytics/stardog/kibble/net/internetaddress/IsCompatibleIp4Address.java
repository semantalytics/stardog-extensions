package com.semantalytics.stardog.kibble.net.internetaddress;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.net.InetAddresses;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.net.Inet6Address;
import java.net.InetAddress;

import static com.google.common.net.InetAddresses.*;
import static com.stardog.stark.Values.literal;

public class IsCompatibleIp4Address extends AbstractFunction implements UserDefinedFunction {

    public IsCompatibleIp4Address() {
        super(1, InternetAddressVocabulary.isIp4MappedAddress.toString());
    }

    private IsCompatibleIp4Address(final IsCompatibleIp4Address internetAddressToNumber) {
        super(internetAddressToNumber);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final InetAddress ip = Inet6Address.getByAddress().forString(((Literal)values[0]).label()).;

            return ValueOrError.General.of(literal(isCompatIPv4Address(ip)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Function copy() {
        return new IsCompatibleIp4Address(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetAddressVocabulary.isIp4MappedAddress.toString();
    }

}
