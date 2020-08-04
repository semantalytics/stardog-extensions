package com.semantalytics.stardog.kibble.function;

import com.complexible.stardog.plan.filter.AbstractExpression;
import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.ValueSolution;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.FunctionDefinition;
import com.complexible.stardog.plan.filter.functions.FunctionRegistry;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.stardog.stark.IRI;
import com.stardog.stark.Literal;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertIntegerLiteral;
import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertLiteral;
import static java.util.stream.Collectors.toList;

public final class Memoize extends AbstractExpression implements UserDefinedFunction {

    private Cache<Integer, ValueOrError> cache;

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

    protected Memoize() {
        super(new Expression[0]);
    }

    private Memoize(final Memoize memoize) {
        super(memoize);
    }

    @Override
    public String getName() {
        return FunctionVocabulary.memoize.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public Memoize copy() {
        return new Memoize(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public ValueOrError evaluate(ValueSolution valueSolution) {

        if(getArgs().size() >= 2) {
            final ValueOrError firstArgValueOrError = getFirstArg().evaluate(valueSolution);
            if(!firstArgValueOrError.isError() && assertIntegerLiteral(firstArgValueOrError.value())) {
                final long cacheSize = Literal.longValue((Literal)firstArgValueOrError.value());

                if(cache == null) {
                    cache = CacheBuilder.newBuilder().maximumSize(cacheSize).build();
                }

                final ValueOrError secondArgValueOrError = getSecondArg().evaluate(valueSolution);

                if(!secondArgValueOrError.isError()) {

                    final String functionIri;

                    if (assertLiteral(firstArgValueOrError.value())) {
                        functionIri = ((Literal) secondArgValueOrError.value()).label();
                    } else if (firstArgValueOrError instanceof IRI) {
                        functionIri = secondArgValueOrError.toString();
                    } else {
                        return ValueOrError.Error;
                    }

                    final List<Expression> functionArgs = getArgs().stream().skip(1).collect(toList());

                    Optional<ValueOrError> cachedValueOrError = Optional.ofNullable(cache.getIfPresent(Objects.hash(functionIri, functionArgs, valueSolution)));

                    if (cachedValueOrError.isPresent()) {
                        return cachedValueOrError.get();
                    } else {
                        ValueOrError valueOrError = functionRegistry.get(functionIri, functionArgs, null).evaluate(valueSolution);
                        cache.put(Objects.hash(functionIri, functionArgs, valueSolution), valueOrError);
                        return valueOrError;
                    }

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
        return FunctionVocabulary.memoize.toString();
    }
}