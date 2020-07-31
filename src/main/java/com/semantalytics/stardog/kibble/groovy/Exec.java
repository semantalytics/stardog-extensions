package com.semantalytics.stardog.kibble.groovy;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.api.client.util.Lists;
import com.google.common.collect.Range;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import com.semantalytics.stardog.kibble.string.StringVocabulary;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import groovy.grape.Grape;
import groovy.grape.GrapeEngine;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static com.stardog.stark.Values.literal;

public final class Exec extends AbstractFunction implements StringFunction {

    protected Exec() {
        super(Range.atLeast(1), "http://semantalytics.com/2020/07/ns/kibbles/groovy/exec");
    }

    private Exec(final Exec exec) {
        super(exec);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        try {
            GroovyScriptEngine engine = new GroovyScriptEngine(new URL[]{new File(System.getProperty("STARDOG_EXT") + "/groovy/").toURI().toURL()});
            GroovyShell shell = new GroovyShell();
            Object result = shell.evaluate(new File("/opt/stardog-ext/groovy/HelloWorld.groovy").toURI());
            System.setProperty("grape.root", "/" + System.getProperty("STARDOG_EXT") + "/groovy/libs");
            Binding binding = new Binding();
            //Object result = engine.run("HelloWorld.groovy", binding);
            return ValueOrError.General.of(literal(result.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ValueOrError.Error;
    }

        @Override
    public Exec copy() {
        return new Exec(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return "";
    }
}
