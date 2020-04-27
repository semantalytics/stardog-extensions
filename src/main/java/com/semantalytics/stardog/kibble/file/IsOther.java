


package com.semantalytics.stardog.kibble.file;

import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class IsOther extends AbstractFunction implements UserDefinedFunction {

    IsOther() {
        super(1, FileVocabulary.isOther.stringValue());
    }

    private IsOther(final IsOther isOther) {
        super(isOther);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String file = assertStringLiteral(values[0]).stringValue();

        final Boolean isOther;

        try {
            isOther = Files.readAttributes(Paths.get(file), BasicFileAttributes.class).isOther();
        } catch (IOException e) {
            throw new ExpressionEvaluationException(e);
        }

        return Values.literal(isOther);
    }

    @Override
    public IsOther copy() {
        return new IsOther(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return FileVocabulary.isOther.name();
    }
}
