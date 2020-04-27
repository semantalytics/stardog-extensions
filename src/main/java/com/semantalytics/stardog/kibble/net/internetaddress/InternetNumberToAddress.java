package com.semantalytics.stardog.kibble.net.internetaddress;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.net.InetAddresses;
import com.google.common.primitives.UnsignedLong;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;

public class InternetNumberToAddress extends AbstractFunction implements UserDefinedFunction {

    public InternetNumberToAddress() {
        super(1, InternetAddressVocabulary.toAddress.stringValue());
    }

    private InternetNumberToAddress(InternetNumberToAddress internetNumberToAddress) {
        super(internetNumberToAddress);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final long ipNumber = assertNumericLiteral(values[0]).longValue();

        return literal(InetAddresses.fromInteger(UnsignedLong.valueOf(ipNumber).intValue()).getHostAddress());
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
