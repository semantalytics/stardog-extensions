package com.semantalytics.stardog.function.util;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.semantalytics.stardog.kibble.util.UtilVocabulary;
import com.stardog.stark.Value;

public class SlugifyCaseInsensitive extends AbstractFunction implements UserDefinedFunction {

    private static final com.github.slugify.Slugify slug;

    static {
        slug = new com.github.slugify.Slugify();
        slug.withLowerCase(true);
    }

    protected SlugifyCaseInsensitive() {
        super(1, UtilVocabulary.slugifyCaseInsensitive.stringValue());
    }

    public SlugifyCaseInsensitive(final SlugifyCaseInsensitive slugify) {
        super(slugify);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        return null;
    }

    @Override
    public Function copy() {
        return new SlugifyCaseInsensitive(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return UtilVocabulary.slugifyCaseInsensitive.name();
    }
}
