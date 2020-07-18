package com.semantalytics.stardog.kibble.net.internetaddress;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.net.InetAddresses;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;

import static com.google.common.net.InetAddresses.toAddrString;
import static com.stardog.stark.Values.literal;

public class ToAddrString extends AbstractFunction implements UserDefinedFunction {

    public ToAddrString() {
        super(1, InternetAddressVocabulary.convertDottedQuadToHex.toString());
    }

    private ToAddrString(final ToAddrString toAddrString) {
        super(toAddrString);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final InetAddress inetAddress = InetAddresses.forString(((Literal)values[0]).label());

            if(inetAddress instanceof Inet4Address) {
                return ValueOrError.General.of(literal(toAddrString(inetAddress), InternetAddressVocabulary.ipv4AddressDt));
            }
            else if(inetAddress instanceof Inet6Address) {
                return ValueOrError.General.of(literal(toAddrString(inetAddress), InternetAddressVocabulary.ipv6AddressDt));
            } else {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Function copy() {
        return new ToAddrString(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetAddressVocabulary.convertDottedQuadToHex.toString();
    }

}
