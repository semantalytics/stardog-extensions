
package com.semantalytics.stardog.kibble.xml;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.semantalytics.stardog.kibble.file.FileVocabulary;
import org.openrdf.model.Value;

public class Xpath extends AbstractFunction implements UserDefinedFunction {

    Xpath() {
        super(1, XmlVocabulary.xPath.stringValue());
    }

    private Xpath(final Xpath contentType) {
        super(contentType);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        return null;
    }

    @Override
    public Function copy() {
        return new Xpath(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return FileVocabulary.contentType.name();
    }
}
