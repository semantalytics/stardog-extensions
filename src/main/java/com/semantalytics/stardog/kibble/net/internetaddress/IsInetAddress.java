package com.semantalytics.stardog.kibble.net.internetaddress;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.net.InetAddresses;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.net.InetAddress;

import static com.google.common.net.InetAddresses.coerceToInteger;
import static com.stardog.stark.Values.literal;

public class IsInetAddress extends AbstractFunction implements UserDefinedFunction {

    public IsInetAddress() {
        super(1, InternetAddressVocabulary.isInetAddress.toString());
    }

    private IsInetAddress(final IsInetAddress isInetAddress) {
        super(isInetAddress);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String ip = ((Literal)values[0]).label();
            try {
                final boolean isInetAddress = InetAddresses.isInetAddress(ip);

                return ValueOrError.Boolean.of(isInetAddress);
            } catch(IllegalArgumentException e) {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Function copy() {
        return new IsInetAddress(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetAddressVocabulary.isInetAddress.toString();
    }

}
