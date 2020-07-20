package com.semantalytics.stardog.kibble.net.internetaddress;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.net.InetAddresses;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.net.Inet6Address;
import java.net.InetAddress;

import static com.google.common.net.InetAddresses.get6to4IPv4Address;
import static com.stardog.stark.Values.literal;

public class Get6to4IpV4Address extends AbstractFunction implements UserDefinedFunction {

    public Get6to4IpV4Address() {
        super(1, InternetAddressVocabulary.get6to4IPv4Address.toString());
    }

    private Get6to4IpV4Address(final Get6to4IpV4Address get6to4IpV4Address) {
        super(get6to4IpV4Address);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final InetAddress inetAddress = InetAddresses.forString(((Literal)values[0]).label());

            if(inetAddress instanceof Inet6Address) {
                return ValueOrError.General.of(literal(get6to4IPv4Address((Inet6Address) inetAddress).toString(), InternetAddressVocabulary.ipv6AddressDt));
            } else {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Get6to4IpV4Address copy() {
        return new Get6to4IpV4Address(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetAddressVocabulary.get6to4IPv4Address.toString();
    }

}
