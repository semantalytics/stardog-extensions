package com.semantalytics.stardog.kibble.string;

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
import com.stardog.stark.Values;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.*;

public class NGram extends AbstractExpression implements UserDefinedFunction {

    public NGram() {
        super(new Expression[0]);
    }

    public NGram(final NGram nGram) {
        super(nGram);
    }

    @Override
    public String getName() {
        return StringVocabulary.ngram.toString();
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
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public ValueOrError evaluate(final ValueSolution valueSolution) {
        if(getArgs().size() == 2) {
            final ValueOrError firstArgValueOrError = getFirstArg().evaluate(valueSolution);
            if(!firstArgValueOrError.isError() && assertIntegerLiteral(firstArgValueOrError.value())) {
                final int n = Literal.intValue((Literal) firstArgValueOrError.value());

                final ValueOrError secondArgValueOrError = getSecondArg().evaluate(valueSolution);
                if (!secondArgValueOrError.isError() && assertStringLiteral(secondArgValueOrError.value())) {
                    final char[] tokens = ((Literal) secondArgValueOrError.value()).label().toCharArray();
                    final MappingDictionary mappingDictionary = valueSolution.getDictionary();
                    long[] ngrams = IntStream.rangeClosed(0, tokens.length - n)
                            .mapToLong(i -> mappingDictionary.add(Values.literal(String.valueOf(Arrays.copyOfRange(tokens, i, i + n)))))
                            .toArray();
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
