package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class ReplaceEach extends AbstractFunction implements StringFunction {

    protected ReplaceEach() {
        super(3, StringVocabulary.replaceEach.toString());
    }

    private ReplaceEach(final ReplaceEach replaceEach) {
        super(replaceEach);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        for(final Value value : values) {
            if(!assertStringLiteral(value)) {
                return ValueOrError.Error;
            }
        }

        final String string = ((Literal)values[0]).label();
        final String[] searchList = ((Literal)values[1]).label().split("\u001f");
        final String[] replacementList = ((Literal)values[2]).label().split("\u001f");

        if(searchList.length != replacementList.length) {
            return ValueOrError.Error;
        }

        return ValueOrError.General.of(literal(replaceEach(string, searchList, replacementList)));
    }

    @Override
    public ReplaceEach copy() {
        return new ReplaceEach(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.replaceEach.toString();
    }
}
