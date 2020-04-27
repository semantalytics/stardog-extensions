package com.semantalytics.stardog.kibble.net.internetdomainname;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.net.InternetDomainName;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.*;

public class TopDomainUnderRegistrySuffix extends AbstractFunction implements UserDefinedFunction {

    public TopDomainUnderRegistrySuffix() {
        super(1, InternetDomainNameVocabulary.topDomainUnderRegistrySuffix.stringValue());
    }

    private TopDomainUnderRegistrySuffix(final TopDomainUnderRegistrySuffix topDomainUnderRegistrySuffix) {
        super(topDomainUnderRegistrySuffix);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String name = assertStringLiteral(values[0]).stringValue();

        return literal(InternetDomainName.from(name).topDomainUnderRegistrySuffix().toString());
    }

    @Override
    public TopDomainUnderRegistrySuffix copy() {
        return new TopDomainUnderRegistrySuffix(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetDomainNameVocabulary.topDomainUnderRegistrySuffix.name();
    }

}
