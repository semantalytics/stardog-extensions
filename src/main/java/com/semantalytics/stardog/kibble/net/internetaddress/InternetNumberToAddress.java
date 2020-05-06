package com.semantalytics.stardog.kibble.net.internetaddress;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.net.InetAddresses;
import com.google.common.primitives.UnsignedLong;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;

public class InternetNumberToAddress extends AbstractFunction implements UserDefinedFunction {

    public InternetNumberToAddress() {
        super(1, InternetAddressVocabulary.toAddress.stringValue());
    }

    private InternetNumberToAddress(InternetNumberToAddress internetNumberToAddress) {
        super(internetNumberToAddress);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertNumericLiteral(values[0])) {
            final long ipNumber = Literal.longValue((Literal)values[0]);

            return ValueOrError.General.of(literal(InetAddresses.fromInteger(UnsignedLong.valueOf(ipNumber).intValue()).getHostAddress()));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Function copy() {
        return new InternetNumberToAddress(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetAddressVocabulary.toAddress.name();
    }
}
