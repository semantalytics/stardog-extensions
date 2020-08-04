package com.semantalytics.stardog.kibble.array;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.index.dictionary.MappingDictionary;
import com.complexible.stardog.plan.filter.AbstractExpression;
import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.ValueSolution;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Lists;
import com.stardog.stark.Value;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertArrayLiteral;
import static java.util.stream.Collectors.toList;

public final class NDims extends AbstractExpression implements UserDefinedFunction {

    public NDims() {
        super(new Expression[0]);
    }

    public NDims(final NDims nDims) {
        super(nDims);
    }

    @Override
    public String getName() {
        return ArrayVocabulary.nDims.toString();
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
               int depth = 1;

               List<Value> boundary = LongStream.of(arrayLiteral.getValues()).mapToObj(mappingDictionary::getValue).collect(toList());
               while(!boundary.stream().noneMatch(AbstractFunction::assertArrayLiteral)) {
                   boundary = boundary.stream().filter(AbstractFunction::assertArrayLiteral).map(ArrayLiteral.class::cast).flatMapToLong(v -> LongStream.of(v.getValues())).mapToObj(mappingDictionary::getValue).collect(toList());
                   depth++;
               }

               return ValueOrError.Int.of(depth);
           } else {
               return ValueOrError.Error;
           }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public NDims copy() {
        return new NDims(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.nDims.toString();
    }
}

