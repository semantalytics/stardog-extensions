package com.semantalytics.stardog.kibble.function;

import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.FunctionDefinition;
import com.complexible.stardog.plan.filter.functions.FunctionRegistry;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Range;
import com.stardog.stark.IRI;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.Values;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class Compose extends AbstractFunction implements UserDefinedFunction {

    private final FunctionRegistry functionRegistry = new FunctionRegistry() {

        @Override
        public Iterator<FunctionDefinition> iterator() {
            return iterator();
        }

        @Override
        public FunctionDefinition get(String s) {
            return get(s);
        }

        public FunctionRegistry getInstance() {
            return Instance;
        }

    }.getInstance();

    static Map<String, FunctionDefinition> compositionMap;

    public Compose() {
        super(Range.atLeast(2), FunctionVocabulary.compose.toString());
    }

    public Compose(final Compose compose) {
        super(compose);
    }

    public static Expression get(final String functionName, final List<Expression> args) {
        return compositionMap.get(functionName).getExpression(args, null);
    }

    @Override
    protected ValueOrError internalEvaluate(Value... values) {
        final Value compositeFunction = Values.bnode();
        final String functionF;
        final String functionG;

        if (assertLiteral(values[0])) {
            functionF = ((Literal) values[0]).label();
        } else if (values[0] instanceof IRI) {
            functionF = values[0].toString();
        } else {
            return ValueOrError.Error;
        }

        if (assertLiteral(values[1])) {
            functionG = ((Literal) values[1]).label();
        } else if (values[1] instanceof IRI) {
            functionG = values[1].toString();
        } else {
            return ValueOrError.Error;
        }

        final Expression function;

        if(Compose.compositionMap.containsKey(functionF)) {
            function = Compose.compositionMap.get(functionF).getExpression();
        } else {
            function = functionRegistry.get(functionF).getExpression();
        }

        compositionMap.put(compositeFunction, );
        return ValueOrError.General.of(compositeFunction);
    }

    @Override
    public Compose copy() {
        return new Compose(this);
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);

    }

    @Override
    public void initialize() {
        compositionMap = new HashMap<>();
    }
}