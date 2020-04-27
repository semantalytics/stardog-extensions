package com.semantalytics.stardog.kibble.file;

import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import org.apache.commons.io.Charsets;
import org.openrdf.model.IRI;
import org.openrdf.model.Value;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Paths;

public class Sha1 extends AbstractFunction implements UserDefinedFunction {

    public Sha1() {
        super(1, FileVocabulary.sha1.stringValue());
    }

    public Sha1(final Sha1 sha1) {
        super(sha1);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        //TODO handle IRI or literal, check for file: protocol
        return null;
    }

    @Override
    public Function copy() {
        return new Sha1(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return FileVocabulary.sha1.name();
    }
}
