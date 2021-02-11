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
import com.stardog.stark.Value;
import org.apache.commons.lang3.ArrayUtils;

import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.*;
import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;

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
        final Optional<Value> leftPadToken;
        final Optional<Value> rightPadToken;
        if(getArgs().size() == 4) {
            final ValueOrError thirdArgValueOrError = getThirdArg().evaluate(valueSolution);
            if (!thirdArgValueOrError.isError()) {
                leftPadToken = Optional.ofNullable(thirdArgValueOrError.value());
                final ValueOrError fourthArgValueOrError = getArgs().get(2).evaluate(valueSolution);
                if (!fourthArgValueOrError.isError()) {
                    rightPadToken = Optional.of(fourthArgValueOrError.value());
                } else {
                    return ValueOrError.Error;
                }
            } else {
                return ValueOrError.Error;
            }
        } else {
            leftPadToken = Optional.empty();
            rightPadToken = Optional.empty();
        }

        if(getArgs().size() == 2 || getArgs().size() == 4) {
            final ValueOrError firstArgValueOrError = getFirstArg().evaluate(valueSolution);
            if(!firstArgValueOrError.isError() && assertIntegerLiteral(firstArgValueOrError.value())) {
                final int n = Literal.intValue((Literal) firstArgValueOrError.value());

                final ValueOrError secondArgValueOrError = getSecondArg().evaluate(valueSolution);
                if (!secondArgValueOrError.isError() && assertArrayLiteral(secondArgValueOrError.value())) {
                    final ArrayLiteral arrayLiteral = (ArrayLiteral) secondArgValueOrError.value();
                    final MappingDictionary mappingDictionary = valueSolution.getDictionary();
                    final long[] tokens;
                    if(leftPadToken.isPresent() && rightPadToken.isPresent()) {
                        long[] leftPadTokens = new long[n-1];
                        long[] rightPadTokens = new long[n-1];
                        Arrays.fill(leftPadTokens, mappingDictionary.add(leftPadToken.get()));
                        Arrays.fill(rightPadTokens, mappingDictionary.add(rightPadToken.get()));

                        tokens = Stream.of(leftPadTokens, arrayLiteral.getValues(), rightPadTokens).flatMapToLong(Arrays::stream).toArray();
                    } else{
                        tokens = arrayLiteral.getValues();
                    }
                    long[] ngrams = IntStream.rangeClosed(0, tokens.length - n)
                            .mapToObj(i -> new ArrayLiteral(Arrays.copyOfRange(tokens, i, i + n)))
                            .mapToLong(mappingDictionary::add).toArray();
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
