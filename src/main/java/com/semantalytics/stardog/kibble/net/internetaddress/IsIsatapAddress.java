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

import static com.stardog.stark.Values.literal;

public class IsIsatapAddress extends AbstractFunction implements UserDefinedFunction {

    public IsIsatapAddress() {
        super(1, InternetAddressVocabulary.isIsatapAddress.toString());
    }

    private IsIsatapAddress(final IsIsatapAddress isIsatapAddress) {
        super(isIsatapAddress);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String ip = ((Literal)values[0]).label();
            try {
                final InetAddress inetAddress = InetAddresses.forString(ip);

                if(inetAddress instanceof Inet6Address) {
                    return ValueOrError.General.of(literal(InetAddresses.isIsatapAddress((Inet6Address) inetAddress)));
                } else {
                    return ValueOrError.Error;
                }
            } catch(IllegalArgumentException e) {
                return ValueOrError.Error;
            }

        } else {
            return ValueOrError.Error;
        }

    }

    @Override
    public Function copy() {
        return new IsIsatapAddress(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetAddressVocabulary.isIsatapAddress.toString();
    }

}
