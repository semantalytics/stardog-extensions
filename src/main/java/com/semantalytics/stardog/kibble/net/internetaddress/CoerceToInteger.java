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

import static com.google.common.net.InetAddresses.*;
import static com.stardog.stark.Values.literal;

public class CoerceToInteger extends AbstractFunction implements UserDefinedFunction {

    public CoerceToInteger() {
        super(1, InternetAddressVocabulary.coerceToInteger.toString());
    }

    private CoerceToInteger(final CoerceToInteger coerceToInteger) {
        super(coerceToInteger);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final InetAddress inetAddress = InetAddresses.forString(((Literal)values[0]).label());

            return ValueOrError.General.of(literal(coerceToInteger(inetAddress)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public CoerceToInteger copy() {
        return new CoerceToInteger(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetAddressVocabulary.coerceToInteger.toString();
    }

}
