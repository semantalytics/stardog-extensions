package com.semantalytics.stardog.kibble.array;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.index.dictionary.MappingDictionary;
import com.complexible.stardog.plan.filter.*;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Lists;
import com.stardog.stark.Literal;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertArrayLiteral;
import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertLiteral;
import static java.util.stream.Collectors.toList;

public final class Partition extends AbstractExpression implements UserDefinedFunction {

    public Partition() {
        super(new Expression[0]);
    }

    public Partition(final Partition partition) {
        super(partition);
    }

    @Override
    public String getName() {
        return ArrayVocabulary.partition.toString();
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
               final  List<Long> array = Arrays.stream(((ArrayLiteral)firstArgValue.value()).getValues()).boxed().collect(toList());
                   final ValueOrError secondArgValueOrError = getSecondArg().evaluate(valueSolution);
                   if(!secondArgValueOrError.isError() && assertLiteral(secondArgValueOrError.value()) && EvalUtil.isNumericDatatype(((Literal)secondArgValueOrError.value()).datatype())) {
                       final int size = Literal.intValue((Literal)secondArgValueOrError.value());

                       final MappingDictionary mappingDictionary = valueSolution.getDictionary();

                       long[] ids = Lists.partition(array, size).stream().map(p -> p.stream().mapToLong(Long::longValue).toArray()).map(ArrayLiteral::new).mapToLong(mappingDictionary::add).toArray();

                       return ValueOrError.General.of(new ArrayLiteral(ids));

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
    public Partition copy() {
        return new Partition(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.partition.name();
    }
}

