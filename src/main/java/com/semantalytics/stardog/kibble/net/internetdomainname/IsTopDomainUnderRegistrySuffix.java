package com.semantalytics.stardog.kibble.net.internetdomainname;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.net.InternetDomainName;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.*;

public class IsTopDomainUnderRegistrySuffix extends AbstractFunction implements UserDefinedFunction {

    public IsTopDomainUnderRegistrySuffix() {
        super(1, InternetDomainNameVocabulary.isTopDomainUnderRegistrySuffix.stringValue());
    }

    private IsTopDomainUnderRegistrySuffix(final IsTopDomainUnderRegistrySuffix isTopDomainUnderRegistrySuffix) {
        super(isTopDomainUnderRegistrySuffix);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
        final String name = assertStringLiteral(values[0]).stringValue();

        return literal(InternetDomainName.from(name).isTopDomainUnderRegistrySuffix());
    }

    @Override
    public IsTopDomainUnderRegistrySuffix copy() {
        return new IsTopDomainUnderRegistrySuffix(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetDomainNameVocabulary.isTopDomainUnderRegistrySuffix.name();
    }

}
