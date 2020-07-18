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

public class FromInteger extends AbstractFunction implements UserDefinedFunction {

    public FromInteger() {
        super(1, InternetAddressVocabulary.fromInteger.toString());
    }

    private FromInteger(final FromInteger fromInteger) {
        super(fromInteger);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {

        if(assertNumericLiteral(values[0])) {
            final int inetAddressInt = Literal.intValue((Literal)values[0]);
            try {
                return ValueOrError.General.of(literal(InetAddresses.fromInteger(inetAddressInt).toString()));
            } catch(IllegalArgumentException e) {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Function copy() {
        return new FromInteger(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetAddressVocabulary.fromInteger.toString();
    }

}
