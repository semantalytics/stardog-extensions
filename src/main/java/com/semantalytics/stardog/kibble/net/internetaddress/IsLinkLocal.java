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

import static com.stardog.stark.Values.literal;

/**
 * Given the dotted-quad representation of an IPv4 network address as a string, returns an
 * integer that represents the numeric value of the address in network byte order (big endian)
 */
public class IsLinkLocal extends AbstractFunction implements UserDefinedFunction {

    public IsLinkLocal() {
        super(1, InternetAddressVocabulary.isLinkLocal.toString());
    }

    private IsLinkLocal(final IsLinkLocal internetAddressToNumber) {
        super(internetAddressToNumber);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String ip = ((Literal)values[0]).label();
            try {
                final InetAddress inetAddress = InetAddresses.forString(ip);

                return ValueOrError.General.of(literal(inetAddress.isLinkLocalAddress()));
            } catch(IllegalArgumentException e) {
                return ValueOrError.Error;
            }

        } else {
            return ValueOrError.Error;
        }

    }

    @Override
    public Function copy() {
        return new IsLinkLocal(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetAddressVocabulary.isLinkLocal.toString();
    }

}
