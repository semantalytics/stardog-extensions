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
import com.stardog.stark.Values;
import org.apache.commons.lang.ArrayUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertArrayLiteral;
import static com.stardog.stark.Values.literal;
import static java.util.stream.Collectors.toList;

public final class IndexOf extends AbstractExpression implements UserDefinedFunction {

    public IndexOf() {
        super(new Expression[0]);
    }

    public IndexOf(final IndexOf removeFirst) {
        super(removeFirst);
    }

    @Override
    public String getName() {
        return ArrayVocabulary.indexOf.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public ValueOrError evaluate(final ValueSolution valueSolution) {

        if(getArgs().size() == 2) {
            final ValueOrError firstArgValueOrError = getFirstArg().evaluate(valueSolution);
            if (!firstArgValueOrError.isError() && assertArrayLiteral(firstArgValueOrError.value())) {
                final ArrayLiteral arrayLiteral = (ArrayLiteral) firstArgValueOrError.value();

                final ValueOrError secondArgValueOrError = getSecondArg().evaluate(valueSolution);
                if (!secondArgValueOrError.isError()) {
                    final Value secondArgValue = secondArgValueOrError.value();

                    final MappingDictionary mappingDictionary = valueSolution.getDictionary();

                    List<Value> arrayValues = Arrays.stream(arrayLiteral.getValues()).mapToObj(mappingDictionary::getValue).collect(toList());

                    long[] matchedIndexIds = LongStream.range(0, arrayValues.size()).filter(i -> arrayValues.get((int)i).equals(secondArgValue)).map(i -> mappingDictionary.add(literal(i))).toArray();
                    //TODO Should this return ordinal/offset typed literals?
                    //TODO should this return a mask?
                    //TODO should a function be added to apply a mask?
                    return ValueOrError.General.of(new ArrayLiteral(matchedIndexIds));
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
    public IndexOf copy() {
        return new IndexOf(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.indexOf.name();
    }
}

