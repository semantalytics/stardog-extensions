package com.semantalytics.stardog.lab.function;

import com.complexible.stardog.plan.filter.AbstractExpression;
import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.ValueSolution;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Lists;
import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertIntegerLiteral;
import com.semantalytics.stardog.kibble.string.StringVocabulary;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.Values;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.time.Instant;
import java.util.List;

import static com.stardog.stark.Values.DATATYPE_FACTORY;

public class Benchmark extends AbstractExpression implements UserDefinedFunction {

    public Benchmark() {
        super(new Expression[0]);
    }

    public Benchmark(List<Expression> expressions) {
        super(expressions);
    }

    @Override
    public String getName() {
        return "jdfkjsf;dajjjjhar
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(new String[]{this.getName()});
    }

    public Benchmark(final Benchmark benchmark) {
        super(benchmark);
    }

    @Override
    public ValueOrError evaluate(final ValueSolution valueSolution) {
        if (getArgs().size() == 2) {
            ValueOrError firstValueOrError = getFirstArg().evaluate(valueSolution);
            if (!firstValueOrError.isError() && assertIntegerLiteral(firstValueOrError.value())) {
                int count = Literal.intValue((Literal) firstValueOrError.value());
                //not quite sure this is measuring the right time. Need to check mysql docs.
                Instant start = Instant.now();
                for (int i = 0; i < count; ++i) {
                    getSecondArg().evaluate(valueSolution);
                }
                Instant finish = Instant.now();
                return ValueOrError.Duration.of(DATATYPE_FACTORY.newDuration(java.time.Duration.between(start, finish).toMillis()));
            } else {
                return ValueOrError.Error;
            }
        }
    }

    @Override
    public Benchmark copy() {
        return new Benchmark(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return "benchmark";
    }
}
