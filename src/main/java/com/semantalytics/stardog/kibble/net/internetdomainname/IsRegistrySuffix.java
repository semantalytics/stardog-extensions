package com.semantalytics.stardog.kibble.net.internetdomainname;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.net.InternetDomainName;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.*;

public class IsRegistrySuffix extends AbstractFunction implements UserDefinedFunction {

    public IsRegistrySuffix() {
        super(1, InternetDomainNameVocabulary.isRegistrySuffix.stringValue());
    }

    private IsRegistrySuffix(final IsRegistrySuffix inetAddressToNumber) {
        super(inetAddressToNumber);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String name = assertStringLiteral(values[0]).stringValue();

        return literal(InternetDomainName.from(name).isRegistrySuffix());

    }

    @Override
    public IsRegistrySuffix copy() {
        return new IsRegistrySuffix(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetDomainNameVocabulary.isRegistrySuffix.name();
    }

}
