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

public class ToCompatibleIp4 extends AbstractFunction implements UserDefinedFunction {

    public ToCompatibleIp4() {
        super(1, InternetAddressVocabulary.isIp4MappedAddress.stringValue());
    }

    private ToCompatibleIp4(final ToCompatibleIp4 internetAddressToNumber) {
        super(internetAddressToNumber);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String ip = ((Literal)values[0]).label();

            return ValueOrError.General.of(literal(isMappedIPv4Address(ip)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Function copy() {
        return new ToCompatibleIp4(this);
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
