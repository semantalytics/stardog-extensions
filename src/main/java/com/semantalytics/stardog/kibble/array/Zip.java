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

import java.util.List;
import java.util.stream.IntStream;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertArrayLiteral;
import static java.util.stream.Collectors.toList;

public final class Zip extends AbstractExpression implements UserDefinedFunction {

    public Zip() {
        super(new Expression[0]);
    }

    public Zip(final Zip zip) {
        super(zip);
    }

    @Override
    public String getName() {
        return ArrayVocabulary.zip.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public ValueOrError evaluate(final ValueSolution valueSolution) {

        if(getArgs().size() == 2) {
           final ValueOrError firstArgValue = getFirstArg().evaluate(valueSolution);
           if(!firstArgValue.isError() && assertArrayLiteral(firstArgValue.value())) {
               final  ArrayLiteral firstArrayLiteral = (ArrayLiteral)firstArgValue.value();

               final ValueOrError secondArgValue = getSecondArg().evaluate(valueSolution);
               if(!secondArgValue.isError() && assertArrayLiteral(secondArgValue.value())) {
                   final  ArrayLiteral secondArrayLiteral = (ArrayLiteral)secondArgValue.value();

                   if(firstArrayLiteral.getValues().length == secondArrayLiteral.getValues().length) {

                       final MappingDictionary mappingDictionary = valueSolution.getDictionary();

                       long[] zippedIds = IntStream.range(0, firstArrayLiteral.getValues().length)
                               .mapToObj(i -> mappingDictionary.add(new ArrayLiteral(firstArrayLiteral.getValues()[i], secondArrayLiteral.getValues()[i])))
                               .mapToLong(l -> l).toArray();

                       return ValueOrError.General.of(new ArrayLiteral(zippedIds));
                   } else {
                       return ValueOrError.Error;
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
    public Zip copy() {
        return new Zip(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.zip.toString();
    }
}

