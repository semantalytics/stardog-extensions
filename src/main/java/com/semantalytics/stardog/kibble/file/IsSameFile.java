package com.semantalytics.stardog.kibble.file;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Paths;

import static com.complexible.common.rdf.model.Values.*;

public class IsSameFile extends AbstractFunction implements UserDefinedFunction {

    IsSameFile() {
        super(1, FileVocabulary.isSameFile.stringValue());
    }

    private IsSameFile(final IsSameFile isSameFile) {
        super(isSameFile);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String file1 = assertIRI(values[0]).stringValue();
        final String file2 = assertIRI(values[1]).stringValue();

        try {
            return literal(Files.isSameFile(Paths.get(file1.substring(5)), Paths.get(file2.substring(5))));
        } catch (IOException e) {
            throw new ExpressionEvaluationException(e);
        }
    }

    @Override
    public IsSameFile copy() {
        return new IsSameFile(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return FileVocabulary.isSameFile.name();
    }
}
