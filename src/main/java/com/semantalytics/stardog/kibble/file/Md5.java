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

public class Md5 extends AbstractFunction implements UserDefinedFunction {

    public Md5() {
        super(1, FileVocabulary.md5.stringValue());
    }

    public Md5(final Md5 file) {
        super(file);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        assertIRI(values[0]);

        final File file = new File(values[0].stringValue());
        final String hash;
        try {
            hash = com.google.common.io.Files.hash(file, Hashing.md5()).toString();
        } catch(IOException e) {
            throw new ExpressionEvaluationException("Error while trying to hash file");
        }
        return Values.literal(hash);
    }

    @Override
    public Function copy() {
        return new Md5(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return FileVocabulary.md5.name();
    }
}
