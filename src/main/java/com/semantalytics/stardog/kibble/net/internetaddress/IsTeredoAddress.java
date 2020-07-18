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

public class IsTeredoAddress extends AbstractFunction implements UserDefinedFunction {

    public IsTeredoAddress() {
        super(1, InternetAddressVocabulary.isTeredoAddress.toString());
    }

    private IsTeredoAddress(final IsTeredoAddress isTeredoAddress) {
        super(isTeredoAddress);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final InetAddress inetAddress = InetAddresses.forString(((Literal)values[0]).label());

            if(inetAddress instanceof Inet6Address) {
            return ValueOrError.Boolean.of(InetAddresses.isTeredoAddress((Inet6Address)inetAddress));
            } else {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Function copy() {
        return new IsTeredoAddress(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetAddressVocabulary.isTeredoAddress.toString();
    }

}
