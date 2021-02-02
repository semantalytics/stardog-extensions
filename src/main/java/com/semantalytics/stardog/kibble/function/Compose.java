package com.semantalytics.stardog.kibble.function;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.stardog.stark.*;

import java.util.*;
import java.util.Map;

import static java.util.stream.Collectors.*;

public final class Compose extends AbstractFunction implements UserDefinedFunction {

    static Map<String, List<String>> compositionMap;

    public Compose() {
        super(Range.atLeast(2), FunctionVocabulary.compose.toString());
    }

    public Compose(final Compose compose) {
        super(compose);
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
