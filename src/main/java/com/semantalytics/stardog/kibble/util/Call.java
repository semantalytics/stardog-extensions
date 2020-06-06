package com.semantalytics.stardog.kibble.util;

import com.complexible.stardog.plan.filter.AbstractExpression;
import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.ValueSolution;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.FunctionDefinition;
import com.complexible.stardog.plan.filter.functions.FunctionRegistry;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Lists;
import com.stardog.stark.IRI;

import java.util.Iterator;
import java.util.List;

public class Call extends AbstractExpression implements UserDefinedFunction {

    FunctionRegistry functionRegistry;

    public Call() {
        super(new Expression[0]);
    }

    private Call(final Call call) {
        super(call);
    }

    @Override
    public String getName() {
        return UtilVocabulary.call.stringValue();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public Call copy() {
        return new Call(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public ValueOrError evaluate(ValueSolution valueSolution) {
        ValueOrError firstArgValueOrError = getFirstArg().evaluate(valueSolution);
        if(!firstArgValueOrError.isError() && firstArgValueOrError.value() instanceof IRI) {
            FunctionDefinition functionDefinition = functionRegistry.get(firstArgValueOrError.value().toString());
            return functionDefinition.getExpression(getArgs().subList(1, getArgs().size()), null).evaluate(valueSolution);
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public String toString() {
        return UtilVocabulary.call.name();
    }
}
