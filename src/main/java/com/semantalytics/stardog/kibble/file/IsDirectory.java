package com.semantalytics.stardog.kibble.file;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;
import java.nio.file.Files;

import java.nio.file.Paths;

import static com.complexible.common.rdf.model.Values.*;

public class IsDirectory extends AbstractFunction implements UserDefinedFunction {

    IsDirectory() {
        super(1, FileVocabulary.isDirectory.stringValue());
    }

    private IsDirectory(final IsDirectory isDirectory) {
        super(isDirectory);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String file = assertIRI(values[0]).stringValue();

        return literal(Files.isDirectory(Paths.get(file)));
    }

    @Override
    public IsDirectory copy() {
        return new IsDirectory(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return FileVocabulary.isDirectory.name();
    }
}
