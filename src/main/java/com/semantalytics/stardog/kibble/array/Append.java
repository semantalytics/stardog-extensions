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

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.*;
import static java.util.stream.Collectors.toList;

public final class Append extends AbstractExpression implements UserDefinedFunction {

    public Append() {
        super(new Expression[0]);
    }

    public Append(final Append append) {
        super(append);
    }

    @Override
    public String getName() {
        return ArrayVocabulary.append.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public ValueOrError evaluate(final ValueSolution valueSolution) {

        if(getArgs().size() >= 2) {
           final ValueOrError firstArgValue = getFirstArg().evaluate(valueSolution);
           if(!firstArgValue.isError() && assertArrayLiteral(firstArgValue.value())) {
               final  ArrayLiteral arrayLiteral = (ArrayLiteral)firstArgValue.value();

               final MappingDictionary mappingDictionary = valueSolution.getDictionary();

               final List<ValueOrError> tail = getArgs().stream().skip(1).map(e -> e.evaluate(valueSolution)).collect(toList());

               if(tail.stream().noneMatch(ValueOrError::isError)) {

                   final long[] ids = ArrayUtils.addAll(arrayLiteral.getValues(), tail.stream().map(ValueOrError::value).mapToLong(mappingDictionary::add).toArray());

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
    public Append copy() {
        return new Append(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.append.name();
    }
}

