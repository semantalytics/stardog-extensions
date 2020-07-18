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

public class IsMCNodeLocal extends AbstractFunction implements UserDefinedFunction {

    public IsMCNodeLocal() {
        super(1, InternetAddressVocabulary.isMCNodeLocal.toString());
    }

    private IsMCNodeLocal(final IsMCNodeLocal isMCNodeLocal) {
        super(isMCNodeLocal);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final InetAddress inetAddress = InetAddresses.forString(((Literal)values[0]).label());

            return ValueOrError.Boolean.of(inetAddress.isMCNodeLocal());
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Function copy() {
        return new IsMCNodeLocal(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetAddressVocabulary.isMCNodeLocal.toString();
    }

}
