package com.semantalytics.stardog.kibble.webassembly;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Value;
import io.github.kawamuray.wasmtime.*;
import org.carrot2.shaded.guava.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;

public class Execute extends AbstractFunction implements UserDefinedFunction {

    private static final Store store = new Store();
    private static final Engine engine = store.engine();
    //Guava cache for modules. Evict on time

    public Execute() {
        super(Range.atLeast(1), WebAssemblyVocabulary.exec.toString());
    }

    public Execute(final Execute execute) {
        super(execute);
    }

    @Override
    protected ValueOrError internalEvaluate(Value... values) {

        Hashing.sha1().hashString(values[0].toString(), StandardCharsets.UTF_8).toString();
        //cache the wasm file on filesystem based on sha1 hash of resource name
        //store in $STARDOG_EXT/wasm ???
        // what if STARDOG_EXT isn't set?
        try (
             Module module = Module.fromFile(engine, "./hello.wat");
             Func helloFunc = WasmFunctions.wrap(store, () -> {
                 System.err.println(">>> Calling back...");
                 System.err.println(">>> Hello World!");
             })) {
            Collection<Extern> imports = Arrays.asList(Extern.fromFunc(helloFunc));
            try (Instance instance = new Instance(store, module, imports)) {
                try (Func f = instance.getFunc("run").get()) {
                    WasmFunctions.Consumer0 fn = WasmFunctions.consumer(f);
                    fn.accept();
                }
            }
        }

        return null;
    }

    @Override
    public Execute copy() {
        return new Execute(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}
