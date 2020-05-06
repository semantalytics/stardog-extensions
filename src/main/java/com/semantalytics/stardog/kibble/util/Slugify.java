package com.semantalytics.stardog.kibble.util;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;

public class Slugify extends AbstractFunction implements UserDefinedFunction {

    private static final com.github.slugify.Slugify slug;

    static {
        slug = new com.github.slugify.Slugify();
        slug.withLowerCase(false);
    }

    protected Slugify() {
        super(1, UtilVocabulary.slugify.stringValue());
    }

    public Slugify(final Slugify slugify) {
        super(slugify);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        if(assertStringLiteral(values[0])) {
            final String string = ((Literal)values[0]).label();

            return ValueOrError.General.of(literal(slug.slugify(string)));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Function copy() {
        return new Slugify(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return "slugify";
    }
}
