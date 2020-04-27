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

public final class JsonPath extends AbstractFunction implements UserDefinedFunction {

    public JsonPath() {
        super(2, "http://semantalytics.com/2016/04/ns/stardog/udf/file/jsonPath");
    }

    private JsonPath(final JsonPath jsonPath) {
        super(jsonPath);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

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

        String result = com.jayway.jsonpath.JsonPath.parse(json).read(path);

        return Values.literal(result);

    }

    @Override
    public Function copy() {
        return new JsonPath(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return "JsonPath";
    }
}
