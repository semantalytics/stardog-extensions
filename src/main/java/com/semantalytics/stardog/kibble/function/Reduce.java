package com.semantalytics.stardog.kibble.function;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.index.dictionary.MappingDictionary;
import com.complexible.stardog.plan.filter.*;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.FunctionDefinition;
import com.complexible.stardog.plan.filter.functions.FunctionRegistry;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Lists;
import com.stardog.stark.IRI;
import com.stardog.stark.Literal;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertArrayLiteral;
import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertLiteral;
import static java.util.stream.Collectors.toList;

public final class Reduce extends AbstractExpression implements UserDefinedFunction {


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

    protected Reduce() {
        super(new Expression[0]);
    }

    private Reduce(final Reduce reduce) {
        super(reduce);
    }

    @Override
    public String getName() {
        return FunctionVocabulary.reduce.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public Reduce copy() {
        return new Reduce(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public ValueOrError evaluate(ValueSolution valueSolution) {

        if(getArgs().size() == 2) {
            final ValueOrError firstArgValueOrError = getFirstArg().evaluate(valueSolution);
            if(!firstArgValueOrError.isError()) {
                final ValueOrError secondArgValueOrError = getSecondArg().evaluate(valueSolution);
                if (!secondArgValueOrError.isError() && assertArrayLiteral(secondArgValueOrError.value())) {
                    long[] elements = ((ArrayLiteral)secondArgValueOrError.value()).getValues();
                    if(elements.length != 2) {
                        return ValueOrError.Error;
                    }

                    final String functionIri;

                    if (assertLiteral(firstArgValueOrError.value())) {
                        functionIri = ((Literal) firstArgValueOrError.value()).label();
                    } else if (firstArgValueOrError instanceof IRI) {
                        functionIri = firstArgValueOrError.toString();
                    } else {
                        return ValueOrError.Error;
                    }

                    MappingDictionary dict = valueSolution.getDictionary();

                    List<Expression> expressions = Arrays.stream(((ArrayLiteral) secondArgValueOrError.value()).getValues()).mapToObj(i -> dict.getValue(i)).map(Expressions::constant).collect(toList());

                    //TODO need to check for errors
                    return expressions.stream().skip(2).reduce(functionRegistry.get(functionIri, Lists.newArrayList(expressions.get(0), expressions.get(1)), null),
                            (result, element) -> functionRegistry.get(functionIri, Lists.newArrayList(result, element), null)).evaluate(valueSolution);
                } else {
                    return ValueOrError.Error;
                }
            } else {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public String toString() {
        return FunctionVocabulary.reduce.toString();
    }
}