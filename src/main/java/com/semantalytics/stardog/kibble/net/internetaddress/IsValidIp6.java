package com.semantalytics.stardog.kibble.net.internetaddress;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.apache.commons.validator.routines.InetAddressValidator;

import static com.stardog.stark.Values.literal;

public class IsValidIp6 extends AbstractFunction implements UserDefinedFunction {

    public IsValidIp6() {
        super(1, InternetAddressVocabulary.isValidIp6.stringValue());
    }

    private IsValidIp6(final IsValidIp6 isValidIp6) {
        super(isValidIp6);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String ip = ((Literal)values[0]).label();

            return ValueOrError.General.of(literal(InetAddressValidator.getInstance().isValidInet6Address(ip)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Function copy() {
        return new IsValidIp6(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetAddressVocabulary.isValidIp6.name();
    }

}
