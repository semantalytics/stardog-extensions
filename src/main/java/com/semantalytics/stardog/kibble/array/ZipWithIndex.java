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
import com.stardog.stark.Values;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertArrayLiteral;

public final class ZipWithIndex extends AbstractExpression implements UserDefinedFunction {

    public ZipWithIndex() {
        super(new Expression[0]);
    }

    public ZipWithIndex(final ZipWithIndex zip) {
        super(zip);
    }

    @Override
    public String getName() {
        return ArrayVocabulary.zipWithIndex.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public ValueOrError evaluate(final ValueSolution valueSolution) {

        if (getArgs().size() == 1) {
            final ValueOrError firstArgValue = getFirstArg().evaluate(valueSolution);
            if (!firstArgValue.isError() && assertArrayLiteral(firstArgValue.value())) {
                final ArrayLiteral arrayLiteral = (ArrayLiteral) firstArgValue.value();

                final long[] ids = arrayLiteral.getValues();
                final MappingDictionary mappingDictionary = valueSolution.getDictionary();

                final long[] zippedIds = IntStream.range(0, ids.length).mapToObj(i -> new ArrayLiteral(mappingDictionary.add(Values.literal(i)), ids[i])).mapToLong(mappingDictionary::add).toArray();

                return ValueOrError.General.of(new ArrayLiteral(zippedIds));
            } else {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public ZipWithIndex copy() {
        return new ZipWithIndex(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.zipWithIndex.name();
    }
}

