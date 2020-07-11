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
import com.google.common.collect.Sets;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertArrayLiteral;
import static java.util.stream.Collectors.toList;

public final class CartesianProduct extends AbstractExpression implements UserDefinedFunction {

    public CartesianProduct() {
        super(new Expression[0]);
    }

    public CartesianProduct(final CartesianProduct cartesianProduct) {
        super(cartesianProduct);
    }

    @Override
    public String getName() {
        return ArrayVocabulary.cartesianProduct.toString();
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
               final  List<Long> firstArrayIds = Arrays.stream(((ArrayLiteral)firstArgValue.value()).getValues()).boxed().collect(toList());

               final ValueOrError secondArgValue = getSecondArg().evaluate(valueSolution);
               if(!secondArgValue.isError() && assertArrayLiteral(secondArgValue.value())) {
                   final  List<Long> secondArrayIds = Arrays.stream(((ArrayLiteral)secondArgValue.value()).getValues()).boxed().collect(toList());

                   List<List<Long>> cartesianProduct = Lists.cartesianProduct(firstArrayIds, secondArrayIds);

                   final MappingDictionary mappingDictionary = valueSolution.getDictionary();

                   long[] inner = cartesianProduct.stream().map(a -> a.stream().mapToLong(Long::longValue).toArray()).map(ArrayLiteral::new).mapToLong(mappingDictionary::add).toArray();

               return ValueOrError.General.of(new ArrayLiteral(inner));
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
    public CartesianProduct copy() {
        return new CartesianProduct(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.cartesianProduct.toString();
    }
}

