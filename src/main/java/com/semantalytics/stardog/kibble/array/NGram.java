package com.semantalytics.stardog.kibble.array;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.index.dictionary.MappingDictionary;
import com.complexible.stardog.plan.filter.AbstractExpression;
import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.ValueSolution;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Lists;
import com.stardog.stark.Literal;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertArrayLiteral;
import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertIntegerLiteral;

public class NGram extends AbstractExpression implements UserDefinedFunction {

    public NGram() {
        super(new Expression[0]);
    }

    public NGram(final NGram nGram) {
        super(nGram);
    }

    @Override
    public String getName() {
        return ArrayVocabulary.ngram.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public Function copy() {
        return new NGram(this);
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public ValueOrError evaluate(ValueSolution valueSolution) {
        if(getArgs().size() == 2) {
            final ValueOrError firstArgValueOrError = getFirstArg().evaluate(valueSolution);
            if(!firstArgValueOrError.isError() && assertIntegerLiteral(firstArgValueOrError.value())) {
                final int n = Literal.intValue((Literal) firstArgValueOrError.value());

                final ValueOrError secondArgValueOrError = getSecondArg().evaluate(valueSolution);
                if (!secondArgValueOrError.isError() && assertArrayLiteral(secondArgValueOrError.value())) {
                    final ArrayLiteral arrayLiteral = (ArrayLiteral) secondArgValueOrError.value();
                    final MappingDictionary mappingDictionary = valueSolution.getDictionary();
                    long[] ngrams = IntStream.rangeClosed(0, arrayLiteral.getValues().length - n).mapToObj(i -> new ArrayLiteral(Arrays.copyOfRange(arrayLiteral.getValues(), i, i + n))).mapToLong(mappingDictionary::add).toArray();
                    return ValueOrError.General.of(new ArrayLiteral(ngrams));
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
}
