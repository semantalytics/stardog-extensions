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
        final BNode compositeFunction = Values.bnode();

        List<String> functions = Arrays.stream(values).map(v -> {
            if (assertLiteral(values[0])) {
                return ((Literal) values[0]).label();
            } else {
                return values[0].toString();
            }
        }).collect(toList());

        compositionMap.put(compositeFunction.id(), Lists.reverse(functions));

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