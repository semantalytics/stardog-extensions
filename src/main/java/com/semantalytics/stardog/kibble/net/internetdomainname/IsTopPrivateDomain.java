package com.semantalytics.stardog.kibble.net.internetdomainname;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.net.InternetDomainName;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.*;

public class IsTopPrivateDomain extends AbstractFunction implements UserDefinedFunction {

    public IsTopPrivateDomain() {
        super(1, InternetDomainNameVocabulary.isTopPrivateDomain.stringValue());
    }

    private IsTopPrivateDomain(final IsTopPrivateDomain isTopPrivateDomain) {
        super(isTopPrivateDomain);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String name = assertStringLiteral(values[0]).stringValue();

        return literal(InternetDomainName.from(name).isTopPrivateDomain());
    }

    @Override
    public IsTopPrivateDomain copy() {
        return new IsTopPrivateDomain(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetDomainNameVocabulary.isTopPrivateDomain.name();
    }

}
