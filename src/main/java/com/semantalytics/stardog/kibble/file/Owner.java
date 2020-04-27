package com.semantalytics.stardog.kibble.file;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;
import java.nio.file.Files;

import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFileAttributes;

import static com.complexible.common.rdf.model.Values.*;

public class Owner extends AbstractFunction implements UserDefinedFunction {

    Owner() {
        super(1, FileVocabulary.owner.stringValue());
    }

    private Owner(final Owner contentType) {
        super(contentType);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String file = assertFileIri(assertIRI(values[0]).stringValue());

        try {
            return literal(Files.readAttributes(Paths.get(file.substring(5)), PosixFileAttributes.class).owner().getName());
        } catch (IOException e) {
            throw new ExpressionEvaluationException(e);
        }
    }

    @Override
    public Function copy() {
        return new Owner(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return FileVocabulary.owner.name();
    }

    private String assertFileIri(final String file) throws ExpressionEvaluationException {
        if(!file.startsWith("file:")) {
            throw new ExpressionEvaluationException("IRI protocol must be file:");
        }
        return file;
    }
}
