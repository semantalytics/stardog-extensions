package com.semantalytics.stardog.kibble.net.internetdomainname;

import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.net.InternetDomainName;
import org.openrdf.model.Value;

public class IsPublicSuffix extends AbstractFunction implements UserDefinedFunction {

    public IsPublicSuffix() {
        super(1, InternetDomainNameVocabulary.isPublicSuffix.stringValue());
    }

    private IsPublicSuffix(final IsPublicSuffix isPublicSuffix) {
        super(isPublicSuffix);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String name = assertStringLiteral(values[0]).stringValue();

        return Values.literal(InternetDomainName.from(name).isPublicSuffix());
    }

    @Override
    public IsPublicSuffix copy() {
        return new IsPublicSuffix(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetDomainNameVocabulary.isPublicSuffix.name();
    }

}
