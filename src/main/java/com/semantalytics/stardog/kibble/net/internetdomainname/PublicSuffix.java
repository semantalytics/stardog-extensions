package com.semantalytics.stardog.kibble.net.internetdomainname;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.net.InternetDomainName;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.*;

public class PublicSuffix extends AbstractFunction implements UserDefinedFunction {

    public PublicSuffix() {
        super(1, InternetDomainNameVocabulary.publicSuffix.stringValue());
    }

    private PublicSuffix(final PublicSuffix publicSuffix) {
        super(publicSuffix);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String name = assertStringLiteral(values[0]).stringValue();

        return literal(InternetDomainName.from(name).publicSuffix().toString());
    }

    @Override
    public PublicSuffix copy() {
        return new PublicSuffix(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetDomainNameVocabulary.publicSuffix.name();
    }

}
