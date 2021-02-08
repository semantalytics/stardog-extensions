package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.google.common.base.Strings.*;
import static com.stardog.stark.Values.literal;

public final class Repeat extends AbstractFunction implements StringFunction {

    protected Repeat() {
        super(2, StringVocabulary.repeat.toString());
    }

    private Repeat(final Repeat repeat) {
        super(repeat);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

      if(!assertStringLiteral(values[0]) || !assertIntegerLiteral(values[1])) {
          return ValueOrError.Error;
      }

      final String string = ((Literal)values[0]).label();
      final int count = Literal.intValue((Literal)values[1]);

      return ValueOrError.General.of(literal(repeat(string, count)));
    }

    @Override
    public Repeat copy() {
        return new Repeat(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.repeat.toString();
    }
}
