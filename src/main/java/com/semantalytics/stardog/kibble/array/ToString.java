package com.semantalytics.stardog.kibble.array;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.index.dictionary.MappingDictionary;
import com.complexible.stardog.plan.filter.*;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Lists;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertArrayLiteral;
import static com.stardog.stark.Values.literal;
import static java.util.stream.Collectors.*;

public final class ToString extends AbstractExpression implements UserDefinedFunction {

    public ToString() {
        super(new Expression[0]);
    }

    public ToString(final ToString toString) {
        super(toString);
    }

    @Override
    public String getName() {
        return ArrayVocabulary.toString.toString();
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
               final MappingDictionary mappingDictionary = valueSolution.getDictionary();
               return ValueOrError.General.of(literal(toString(firstArgValue.value(), mappingDictionary)));
           } else {
               return ValueOrError.Error;
           }
       } else {
           return ValueOrError.Error;
       }
    }

    private static String toString(final Value value, final MappingDictionary mappingDictionary) {
        if(assertArrayLiteral(value)) {
            return Stream.of(
                        "[",
                        LongStream.of(((ArrayLiteral)value).getValues())
                                .mapToObj(mappingDictionary::getValue)
                                .map(v -> toString(v, mappingDictionary)).collect(joining(" ")),
                        "]")
                    .collect(joining(" "));
        } else {
            return value.toString();
        }
    }

    @Override
    public ToString copy() {
        return new ToString(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.toString.name();
    }
}

