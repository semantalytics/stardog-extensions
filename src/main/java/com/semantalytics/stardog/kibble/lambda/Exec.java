package com.semantalytics.stardog.kibble.lambda;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.index.dictionary.MappingDictionary;
import com.complexible.stardog.plan.filter.AbstractExpression;
import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.ValueSolution;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.FunctionDefinition;
import com.complexible.stardog.plan.filter.functions.FunctionRegistry;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.stardog.stark.IRI;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Iterator;
import java.util.List;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertArrayLiteral;
import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;
import static java.util.stream.Collectors.toList;

public final class Exec extends AbstractExpression implements UserDefinedFunction {


    FunctionRegistry functionRegistry = new FunctionRegistry() {

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

    protected Exec() {
        super(new Expression[0]);
    }

    private Exec(final Exec exec) {
        super(exec);
    }

    @Override
    public String getName() {
        return "http://semantalytics.com/2020/07/ns/lambda/exec";
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
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
    public ValueOrError evaluate(ValueSolution valueSolution) {

        if(getArgs().size() >= 1) {
            final ValueOrError firstArgValue = getFirstArg().evaluate(valueSolution);
            if(!firstArgValue.isError()) {
                final String functionIri;
                if(assertStringLiteral(firstArgValue.value())) {
                    functionIri = ((Literal) firstArgValue).label();
                } else if(firstArgValue instanceof IRI) {
                    functionIri = firstArgValue.toString();
                } else {
                    return ValueOrError.Error;
                }

                final List<Expression> tail = getArgs().stream().skip(1).collect(toList());

                ValueOrError solution = functionRegistry.get(functionIri, tail, null).evaluate(valueSolution);

                if(solution.isError()) {
                    return ValueOrError.Error;
                }
                    return ValueOrError.General.of(solution.value());
                } else {
                    return ValueOrError.Error;
                }
            } else {
                return ValueOrError.Error;
            }
    }

    @Override
    public String toString() {
        return "";
    }
}
