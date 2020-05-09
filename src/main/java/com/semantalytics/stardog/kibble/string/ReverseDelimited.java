package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class ReverseDelimited extends AbstractFunction implements StringFunction {

    protected ReverseDelimited() {
        super(2, StringVocabulary.reverseDelimited.toString());
    }

    private ReverseDelimited(final ReverseDelimited reverseDelimited) {
        super(reverseDelimited);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(!assertStringLiteral(values[0]) || !assertStringLiteral(values[1])) {
            return ValueOrError.Error;
        }

      final String string = ((Literal)values[0]).label();
      final String separatorChar = ((Literal)values[1]).label();

      if(separatorChar.length() != 1) {
          return ValueOrError.Error;
      } else {
          return ValueOrError.General.of(literal(reverseDelimited(string, separatorChar.charAt(0))));
      }
    }

    @Override
    public ReverseDelimited copy() {
        return new ReverseDelimited(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.reverseDelimited.name();
    }
}
