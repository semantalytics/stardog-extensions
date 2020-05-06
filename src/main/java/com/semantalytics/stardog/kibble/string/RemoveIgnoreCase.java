package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Datatype;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.apache.commons.lang3.StringUtils;

import static com.stardog.stark.Values.literal;

public final class RemoveIgnoreCase extends AbstractFunction implements StringFunction {

    protected RemoveIgnoreCase() {
        super(2, StringVocabulary.removeIgnoreCase.toString());
    }

    private RemoveIgnoreCase(final RemoveIgnoreCase removeIgnoreCase) {
        super(removeIgnoreCase);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(!assertStringLiteral(values[0])) {
            return ValueOrError.Error;
        };

        if(!assertStringLiteral(values[1])) {
            return ValueOrError.Error;
        };

        final String string = ((Literal)values[0]).label();
        final String remove = ((Literal)values[1]).label();

        return ValueOrError.General.of(literal(StringUtils.removeIgnoreCase(string, remove), Datatype.STRING));
    }

    @Override
    public RemoveIgnoreCase copy() {
        return new RemoveIgnoreCase(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.removeIgnoreCase.name();
    }
}
