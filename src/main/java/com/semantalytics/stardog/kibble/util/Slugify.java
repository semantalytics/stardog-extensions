package com.semantalytics.stardog.kibble.util;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;

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
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
        final String string = assertStringLiteral(values[0]).stringValue();

        return literal(slug.slugify(string));
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
