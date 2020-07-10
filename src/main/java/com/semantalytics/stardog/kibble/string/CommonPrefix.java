package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.google.common.base.Strings.*;
import static com.stardog.stark.Values.literal;

public final class CommonPrefix extends AbstractFunction implements StringFunction {

    protected CommonPrefix() {
        super(2, StringVocabulary.commonPrefix.toString());
    }

    private CommonPrefix(final CommonPrefix commonPrefix) {
        super(commonPrefix);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

       if(!assertStringLiteral(values[0]) || !assertStringLiteral(values[1])) {
           return ValueOrError.Error;
       }

      final String firstString = ((Literal)values[0]).label();
      final String secondString = ((Literal)values[1]).label();
      
      return ValueOrError.General.of(literal(commonPrefix(firstString, secondString)));
    }

    @Override
    public CommonPrefix copy() {
        return new CommonPrefix(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.commonPrefix.toString();
    }
}
