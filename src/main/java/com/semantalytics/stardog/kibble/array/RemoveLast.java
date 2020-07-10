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

import java.util.Arrays;
import java.util.List;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertArrayLiteral;

public final class RemoveLast extends AbstractExpression implements UserDefinedFunction {

    public RemoveLast() {
        super(new Expression[0]);
    }

    public RemoveLast(final RemoveLast removeLast) {
        super(removeLast);
    }

    @Override
    public String getName() {
        return ArrayVocabulary.removeLast.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public ValueOrError evaluate(final ValueSolution valueSolution) {

        if(getArgs().size() == 1) {
           final ValueOrError lastArgValue = getFirstArg().evaluate(valueSolution);
           if(!lastArgValue.isError() && assertArrayLiteral(lastArgValue.value())) {
               final  ArrayLiteral arrayLiteral = (ArrayLiteral)lastArgValue.value();

               return ValueOrError.General.of(new ArrayLiteral(Arrays.stream(arrayLiteral.getValues()).limit(arrayLiteral.getValues().length - 1).toArray()));

           } else {
               return ValueOrError.Error;
           }
       } else {
           return ValueOrError.Error;
       }
    }

    @Override
    public RemoveLast copy() {
        return new RemoveLast(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.removeLast.toString();
    }
}

