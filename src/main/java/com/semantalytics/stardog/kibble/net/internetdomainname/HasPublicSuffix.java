package com.semantalytics.stardog.kibble.net.internetdomainname;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.net.InternetDomainName;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.*;

public class HasPublicSuffix extends AbstractFunction implements UserDefinedFunction {

    public HasPublicSuffix() {
        super(1, InternetDomainNameVocabulary.hasPublicSuffix.stringValue());
    }

    private HasPublicSuffix(final HasPublicSuffix hasPublicSuffix) {
        super(hasPublicSuffix);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String name = assertStringLiteral(values[0]).stringValue();

        return literal(InternetDomainName.from(name).hasPublicSuffix());
    }

    @Override
    public HasPublicSuffix copy() {
        return new HasPublicSuffix(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetDomainNameVocabulary.hasPublicSuffix.name();
    }

}
