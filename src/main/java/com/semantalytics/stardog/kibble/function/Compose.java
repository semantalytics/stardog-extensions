package com.semantalytics.stardog.kibble.function;

import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.FunctionRegistry;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.stardog.stark.*;

import java.util.*;
import java.util.Map;

import static java.util.stream.Collectors.*;

public final class Compose extends AbstractFunction implements UserDefinedFunction {

    static Map<String, List<String>> compositionMap = new HashMap<>();

    public Compose() {
        super(Range.atLeast(2), FunctionVocabulary.compose.toString());
    }

    public Compose(final Compose compose) {
        super(compose);
    }

    @Override
    protected ValueOrError internalEvaluate(Value... values) {

        final BNode compositeFunctionName = Values.bnode();
        final List<String> functionComposition = Lists.newArrayListWithCapacity(values.length);

        for(final Value value : values) {
            final String functionName;
            if (assertLiteral(values[0])) {
                functionName = ((Literal) value).label();
            } else if (values[0] instanceof IRI) {
                functionName = value.toString();
            } else {
                return ValueOrError.Error;
            }
            functionComposition.add(functionName);
        }

        compositionMap.put(compositeFunctionName.id(), functionComposition);

        return ValueOrError.General.of(compositeFunctionName);
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
