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

public class IsValidIp4 extends AbstractFunction implements UserDefinedFunction {

    public IsValidIp4() {
        super(1, InternetAddressVocabulary.isValidIp4.toString());
    }

    private IsValidIp4(final IsValidIp4 isValidIp4) {
        super(isValidIp4);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0])) {
            final String ip = ((Literal)values[0]).label();

            return ValueOrError.General.of(literal(InetAddressValidator.getInstance().isValidInet4Address(ip)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Function copy() {
        return new IsValidIp4(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetAddressVocabulary.isValidIp4.toString();
    }

}
