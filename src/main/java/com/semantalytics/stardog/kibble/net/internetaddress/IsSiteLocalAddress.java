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

public class IsSiteLocalAddress extends AbstractFunction implements UserDefinedFunction {

    public IsSiteLocalAddress() {
        super(1, InternetAddressVocabulary.isSiteLocalAddress.toString());
    }

    private IsSiteLocalAddress(final IsSiteLocalAddress isSiteLocalAddress) {
        super(isSiteLocalAddress);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final InetAddress inetAddress = InetAddresses.forString(((Literal)values[0]).label());

            return ValueOrError.Boolean.of(inetAddress.isSiteLocalAddress());
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Function copy() {
        return new IsSiteLocalAddress(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetAddressVocabulary.isSiteLocalAddress.toString();
    }

}
