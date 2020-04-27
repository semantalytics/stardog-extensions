package com.semantalytics.stardog.kibble.file;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.apache.tika.Tika;
import org.openrdf.model.Value;
import java.nio.file.Files;

import java.io.IOException;
import java.nio.file.Paths;

import static com.complexible.common.rdf.model.Values.*;

public class ContentType extends AbstractFunction implements UserDefinedFunction {

    private Tika tika;

    ContentType() {
        super(1, FileVocabulary.contentType.stringValue());
    }

    private ContentType(final ContentType contentType) {
        super(contentType);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
        final String file = assertFileIri(assertIRI(values[0]).stringValue());

        try {
            return literal(tika.detect(Paths.get(file.substring(5))));
        } catch (IOException e) {
            throw new ExpressionEvaluationException(e);
        }
    }

    @Override
    public void initialize() {
        tika = new Tika();
    }

    @Override
    public ContentType copy() {
        return new ContentType(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return FileVocabulary.contentType.name();
    }

    private String assertFileIri(final String file) throws ExpressionEvaluationException {
        if(!file.startsWith("file:")) {
            throw new ExpressionEvaluationException("IRI protocol must be file:");
        }
        return file;
    }
}
