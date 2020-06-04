package com.semantalytics.stardog.kibble.array;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.index.dictionary.MappingDictionary;
import com.complexible.stardog.plan.filter.AbstractExpression;
import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.ValueSolution;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Lists;
import com.stardog.stark.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertArrayLiteral;
import static java.util.stream.Collectors.toList;

public final class Flatten extends AbstractExpression implements UserDefinedFunction {

    public Flatten() {
        super(new Expression[0]);
    }

    public Flatten(final Flatten flatten) {
        super(flatten);
    }

    @Override
    public String getName() {
        return ArrayVocabulary.flatten.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }



    @Override
    public ValueOrError evaluate(final ValueSolution valueSolution) {

        //TODO add depth argument
        if (getArgs().size() == 1) {
            final ValueOrError firstArgValue = getArgs().get(0).evaluate(valueSolution);
            if (!firstArgValue.isError() && assertArrayLiteral(firstArgValue.value())) {

                final MappingDictionary mappingDictionary = valueSolution.getDictionary();

                final List<Long> flattenedArray = new ArrayList<>(getArgs().size());

                for (final long id : ((ArrayLiteral) firstArgValue.value()).getValues()) {

                    final Value aValue = mappingDictionary.getValue(id);

                    if (assertArrayLiteral(aValue)) {
                        flattenedArray.addAll(Arrays.stream(((ArrayLiteral) aValue).getValues()).boxed().collect(toList()));
                    } else {
                        flattenedArray.add(id);
                    }
                }
                return ValueOrError.General.of(new ArrayLiteral(flattenedArray.stream().mapToLong(i -> i).toArray()));
            } else {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Flatten copy() {
        return new Flatten(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.flatten.name();
    }
}

