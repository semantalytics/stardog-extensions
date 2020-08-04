package com.semantalytics.stardog.kibble.function;

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
import com.stardog.stark.Literal;

import java.util.Iterator;
import java.util.List;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertLiteral;
import static java.util.stream.Collectors.toList;

public final class Compose extends AbstractExpression implements UserDefinedFunction {


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

    protected Compose() {
        super(new Expression[0]);
    }

    private Compose(final Compose compose) {
        super(compose);
    }

    @Override
    public String getName() {
        return FunctionVocabulary.compose.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public Compose copy() {
        return new Compose(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public ValueOrError evaluate(ValueSolution valueSolution) {

        if(getArgs().size() >= 1) {
            final ValueOrError firstArgValueOrError = getFirstArg().evaluate(valueSolution);
            if(firstArgValueOrError.isError()) {
                return ValueOrError.Error;
            } else {
                final String functionIri;

                if (assertLiteral(firstArgValueOrError.value())) {
                    functionIri = ((Literal) firstArgValueOrError.value()).label();
                } else if (firstArgValueOrError instanceof IRI) {
                    functionIri = firstArgValueOrError.toString();
                } else {
                    return ValueOrError.Error;
                }

                final List<Expression> functionArgs = getArgs().stream().skip(1).collect(toList());

                return functionRegistry.get(functionIri, functionArgs, null).evaluate(valueSolution);
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public String toString() {
        return FunctionVocabulary.compose.toString();
    }
}