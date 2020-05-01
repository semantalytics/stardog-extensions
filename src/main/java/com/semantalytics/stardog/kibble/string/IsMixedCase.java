package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.apache.commons.lang3.StringUtils;

import static com.stardog.stark.Values.literal;

public final class IsMixedCase extends AbstractFunction implements StringFunction {

    protected IsMixedCase() {
        super(1, StringVocabulary.isMixedCase.toString());
    }

    private IsMixedCase(final IsMixedCase isMixedCase) {
        super(isMixedCase);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
      
      if(!assertStringLiteral(values[0])) {
          return ValueOrError.Error;
      }

      final String string = ((Literal)values[0]).label();

      return ValueOrError.General.of(literal(StringUtils.isMixedCase(string)));
    }

    @Override
    public IsMixedCase copy() {
        return new IsMixedCase(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.isMixedCase.name();
    }
}
