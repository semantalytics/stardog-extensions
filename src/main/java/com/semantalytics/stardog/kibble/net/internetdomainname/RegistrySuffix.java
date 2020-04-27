package com.semantalytics.stardog.kibble.net.internetdomainname;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.net.InternetDomainName;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.*;

public class RegistrySuffix extends AbstractFunction implements UserDefinedFunction {

    public RegistrySuffix() {
        super(1, InternetDomainNameVocabulary.registrySuffix.stringValue());
    }

    private RegistrySuffix(final RegistrySuffix registrySuffix) {
        super(registrySuffix);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String name = assertStringLiteral(values[0]).stringValue();

        return literal(InternetDomainName.from(name).registrySuffix().toString());
    }

    @Override
    public RegistrySuffix copy() {
        return new RegistrySuffix(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetDomainNameVocabulary.registrySuffix.name();
    }

}
