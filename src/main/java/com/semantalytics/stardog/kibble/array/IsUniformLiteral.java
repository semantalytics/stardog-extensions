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
import com.stardog.stark.IRI;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertArrayLiteral;
import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertLiteral;
import static java.util.stream.Collectors.toList;

public final class IsUniformLiteral extends AbstractExpression implements UserDefinedFunction {

    public IsUniformLiteral() {
        super(new Expression[0]);
    }

    public IsUniformLiteral(final IsUniformLiteral isUniformLiteral) {
        super(isUniformLiteral);
    }

    @Override
    public String getName() {
        return ArrayVocabulary.isUniformLiteral.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    static Stream<Value> flatten(Stream<Value> stream, MappingDictionary mappingDictionary) {
    return stream.flatMap((v) ->
        (assertArrayLiteral(v)) ? flatten(Arrays.stream(((ArrayLiteral)v).getValues()).mapToObj(mappingDictionary::getValue), mappingDictionary) : Stream.of(v)
    );
}

    @Override
    public ValueOrError evaluate(final ValueSolution valueSolution) {

        if(getArgs().size() == 1) {
           final ValueOrError firstArgValue = getFirstArg().evaluate(valueSolution);
           if(!firstArgValue.isError() && assertArrayLiteral(firstArgValue.value())) {
               final  ArrayLiteral arrayLiteral = (ArrayLiteral)firstArgValue.value();

               final MappingDictionary mappingDictionary = valueSolution.getDictionary();

               final List<Value> values = flatten(Arrays.stream(arrayLiteral.getValues()).mapToObj(mappingDictionary::getValue), mappingDictionary).collect(toList());

               final boolean isUniformLiteral;
               if(assertLiteral(values.get(0))) {
                    isUniformLiteral = values.stream().allMatch(AbstractFunction::assertLiteral) &&
                                values.stream().map(Literal.class::cast).map(Literal::datatype).allMatch(l -> l.equals(((Literal)values.get(0)).datatype()));
               } else {
                   isUniformLiteral = false;
               }

               return ValueOrError.Boolean.of(isUniformLiteral);
           } else {
               return ValueOrError.Error;
           }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public IsUniformLiteral copy() {
        return new IsUniformLiteral(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.isUniformLiteral.name();
    }
}

