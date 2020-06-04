package com.semantalytics.stardog.kibble.array;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.plan.filter.AbstractExpression;
import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.ValueSolution;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Lists;

import java.util.List;

import static java.util.stream.Collectors.toList;

public final class Of extends AbstractExpression implements UserDefinedFunction {

    public Of() {
        super(new Expression[0]);
    }

    public Of(final Of of) {
        super(of);
    }

    @Override
    public String getName() {
        return ArrayVocabulary.of.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public ValueOrError evaluate(final ValueSolution valueSolution) {

       final List<ValueOrError> valueOrErrors = getArgs().stream().map(e -> e.evaluate(valueSolution)).collect(toList());

       if(valueOrErrors.stream().anyMatch(ValueOrError::isError)) {
           return ValueOrError.Error;
       } else {
           long[] ids = valueOrErrors.stream().map(ValueOrError::value).mapToLong(v -> valueSolution.getDictionary().add(v)).toArray();
           return ValueOrError.General.of(new ArrayLiteral(ids));
       }
    }

    @Override
    public Of copy() {
        return new Of(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.of.name();
    }
}

