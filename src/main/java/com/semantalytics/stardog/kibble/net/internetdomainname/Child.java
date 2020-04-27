package com.semantalytics.stardog.kibble.net.internetdomainname;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.net.InternetDomainName;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.*;

public class Child extends AbstractFunction implements UserDefinedFunction {

    public Child() {
        super(2, InternetDomainNameVocabulary.child.stringValue());
    }

    private Child(final Child child) {
        super(child);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String name = assertStringLiteral(values[0]).stringValue();
        final String leftParts = assertStringLiteral(values[1]).stringValue();

        return literal(InternetDomainName.from(name).child(leftParts).toString());
    }

    @Override
    public Child copy() {
        return new Child(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return InternetDomainNameVocabulary.child.name();
    }

}
