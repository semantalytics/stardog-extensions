package com.semantalytics.stardog.kibble.json;

import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.jayway.jsonpath.JsonPath;
import org.openrdf.model.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

public final class PathFunction extends AbstractFunction implements UserDefinedFunction {

    public PathFunction() {
        super(2, JsonVocabulary.path.stringValue());
    }

    private PathFunction(final PathFunction pathFunction) {
        super(pathFunction);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {
        // check for file: iri or string literal
        assertIRI(values[0]);
        assertStringLiteral(values[1]);

        String json;

        URLConnection conn;

        try {
            conn = new URL(values[0].stringValue()).openConnection();
        } catch(IOException e) {
            throw new ExpressionEvaluationException(e);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
            json = reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new ExpressionEvaluationException(e);
        }

        final String path = values[1].stringValue();

        String result = JsonPath.parse(json).read(path);

        return Values.literal(result);

    }

    @Override
    public Function copy() {
        return new PathFunction(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return JsonVocabulary.path.name();
    }
}
