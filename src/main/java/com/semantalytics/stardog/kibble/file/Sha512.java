package com.semantalytics.stardog.kibble.file;

import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.hash.Hashing;
import org.openrdf.model.Value;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class Sha512 extends AbstractFunction implements UserDefinedFunction {

    public Sha512() {
        super(1, FileVocabulary.sha512.stringValue());
    }

    public Sha512(final Sha512 file) {
        super(file);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        assertIRI(values[0]);

        final File file = new File(URI.create(values[0].stringValue()));
        final String hash;
        try {
            hash = com.google.common.io.Files.hash(file, Hashing.sha512()).toString();
        } catch(IOException e) {
            throw new ExpressionEvaluationException("Error while trying to hash file");
        }
        return Values.literal(hash);
    }

    @Override
    public Function copy() {
        return new Sha512(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return FileVocabulary.sha512.name();
    }
}
