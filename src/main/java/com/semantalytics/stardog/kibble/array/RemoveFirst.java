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
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertArrayLiteral;
import static java.util.stream.Collectors.toList;

public final class RemoveFirst extends AbstractExpression implements UserDefinedFunction {

    public RemoveFirst() {
        super(new Expression[0]);
    }

    public RemoveFirst(final RemoveFirst removeFirst) {
        super(removeFirst);
    }

    @Override
    public String getName() {
        return ArrayVocabulary.removeFirst.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public ValueOrError evaluate(final ValueSolution valueSolution) {

        if(getArgs().size() == 1) {
           final ValueOrError firstArgValue = getFirstArg().evaluate(valueSolution);
           if(!firstArgValue.isError() && assertArrayLiteral(firstArgValue.value())) {
               final  ArrayLiteral arrayLiteral = (ArrayLiteral)firstArgValue.value();

               final MappingDictionary mappingDictionary = valueSolution.getDictionary();

               return ValueOrError.General.of(new ArrayLiteral(Arrays.stream(arrayLiteral.getValues()).skip(1).toArray()));
               } else {
                   return ValueOrError.Error;
               }
           } else {
               return ValueOrError.Error;
           }
    }

    @Override
    public RemoveFirst copy() {
        return new RemoveFirst(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.removeFirst.toString();
    }
}

