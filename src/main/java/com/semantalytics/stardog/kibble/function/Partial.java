package com.semantalytics.stardog.kibble.function;

import com.complexible.stardog.plan.filter.AbstractExpression;
import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.ValueSolution;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.stardog.stark.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public final class Partial extends AbstractExpression implements UserDefinedFunction {

    static Map<String, List<Expression>> partialMap = new HashMap<>();

    public Partial() {
        super(new Expression[0]);
    }

    public Partial(final Partial partial) {
        super(partial);
    }

    @Override
    public String getName() {
        return FunctionVocabulary.partial.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public ValueOrError evaluate(final ValueSolution valueSolution) {

        final BNode partialFunctionName = Values.bnode();

        partialMap.put(partialFunctionName.id(), getArgs().stream().collect(toList()));

        return ValueOrError.General.of(partialFunctionName);
    }

    @Override
    public Partial copy() {
        return new Partial(this);
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public void initialize() {
        partialMap = new HashMap<>();
    }
}
