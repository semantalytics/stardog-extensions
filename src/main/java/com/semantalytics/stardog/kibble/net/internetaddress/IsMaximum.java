package com.semantalytics.stardog.kibble.net.internetaddress;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.net.InetAddresses;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;

public class IsMaximum extends AbstractFunction implements UserDefinedFunction {

    public IsMaximum() {
        super(1, InternetAddressVocabulary.isMaximum.toString());
    }

    private IsMaximum(final IsMaximum isMaximum) {
        super(isMaximum);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String ip = ((Literal)values[0]).label();

        return ValueOrError.Boolean.of(InetAddresses.isMaximum(InetAddresses.forString(ip)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Function copy() {
        return new IsMaximum(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetAddressVocabulary.isMaximum.toString();
    }

}
