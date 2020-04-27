package com.semantalytics.stardog.kibble.javascript;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Range;
import org.openrdf.model.Value;

import javax.script.*;

import java.util.Arrays;

import static com.complexible.common.rdf.model.Values.literal;
import static java.util.stream.Collectors.toList;

public class Execute extends AbstractFunction implements UserDefinedFunction {

    public Execute() {
        super(Range.atLeast(1), JavascriptVocabulary.exec.stringValue());
    }

    public Execute(final Execute execute) {
        super(execute);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final String script = assertStringLiteral(values[0]).stringValue();
        
        final ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("javascript");
        FilesystemFolder rootFolder = FilesystemFolder.create(new File("/path/to/npm/moduels"), "UTF-8");
        Require.enable(scriptEngine, rootFolder);
        final Bindings bindings = scriptEngine.createBindings();
        
        bindings.put("values", Arrays.stream(values).skip(1).collect(toList()));
        
        try {
            return literal(scriptEngine.eval(script, bindings).toString());
        } catch (final ScriptException e) {
            throw new ExpressionEvaluationException(e);
        }
    }

    @Override
    public Execute copy() {
        return new Execute(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return JavascriptVocabulary.exec.name();
    }
}
