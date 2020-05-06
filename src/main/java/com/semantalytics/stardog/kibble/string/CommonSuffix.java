package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.base.Strings;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;

public final class CommonSuffix extends AbstractFunction implements StringFunction {

    protected CommonSuffix() {
        super(2, StringVocabulary.commonSuffix.toString());
    }

    private CommonSuffix(final CommonSuffix commonSuffix) {
        super(commonSuffix);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(!assertStringLiteral(values[0]) || !assertStringLiteral(values[1])) {
            return ValueOrError.Error;
        }

        final String firstString = ((Literal)values[0]).label();
        final String secondString = ((Literal)values[1]).label();

      return ValueOrError.General.of(literal(Strings.commonSuffix(firstString, secondString)));
    }

    @Override
    public CommonSuffix copy() {
        return new CommonSuffix(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.commonSuffix.name();
    }
}
