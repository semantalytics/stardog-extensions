package com.semantalytics.stardog.kibble.net.internetaddress;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.net.InetAddresses;
import com.stardog.stark.IRI;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;

import static com.stardog.stark.Values.literal;

public class Decrement extends AbstractFunction implements UserDefinedFunction {

    public Decrement() {
        super(1, InternetAddressVocabulary.decrement.toString());
    }

    private Decrement(final Decrement decrement) {
        super(decrement);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final InetAddress inetAddress = InetAddresses.forString(((Literal)values[0]).label());
            final IRI dt;
            if(inetAddress instanceof Inet4Address) {
                dt = InternetAddressVocabulary.ipv4AddressDt;
            } else if(inetAddress instanceof Inet6Address) {
                dt = InternetAddressVocabulary.ipv6AddressDt;
            } else {
                return ValueOrError.Error;
            }
            return ValueOrError.General.of(literal(InetAddresses.decrement(inetAddress).toString(), dt));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Function copy() {
        return new Decrement(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetAddressVocabulary.decrement.toString();
    }

}
