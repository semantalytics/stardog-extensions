package com.semantalytics.stardog.kibble.file;

import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

public final class Read extends AbstractFunction implements UserDefinedFunction {

    Read() {
        super(1, FileVocabulary.read.stringValue());
    }

    private Read(final Read cat) {
        super(cat);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
        assertIRI(values[0]);
        assertStringLiteral(values[1]);

        final URLConnection conn;
        final String text;

        try {
            conn = new URL(values[0].stringValue()).openConnection();
        } catch(IOException e) {
            throw new ExpressionEvaluationException(e);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
             text = reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new ExpressionEvaluationException(e);
        }

        return Values.literal(text);
    }

    @Override
    public Function copy() {
        return new Read(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return FileVocabulary.read.name();
    }
}
