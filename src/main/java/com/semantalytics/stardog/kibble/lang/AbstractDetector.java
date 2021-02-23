package com.semantalytics.stardog.kibble.lang;

import com.complexible.stardog.plan.filter.AbstractExpression;
import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ValueSolution;
import com.complexible.stardog.plan.filter.expr.Constant;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.github.pemistahl.lingua.api.Language;
import com.github.pemistahl.lingua.api.LanguageDetector;
import com.github.pemistahl.lingua.api.LanguageDetectorBuilder;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.Set;
import java.util.stream.Stream;

public abstract class AbstractDetector extends AbstractExpression {

    AbstractDetector(Expression[] expression) {
        super(expression);
    }

    AbstractDetector(AbstractDetector abstractDetector) {
        super(abstractDetector);
    }

    public static final boolean assertNoErrors(Stream<Expression> args, ValueSolution valueSolution) {
        return args.skip(1).map(e -> e.evaluate(valueSolution)).noneMatch(ValueOrError::isError);
    }

    public static final boolean assertAllConstant(Stream<Expression> args, ValueSolution valueSolution) {
        return !args.map(e -> e.evaluate(valueSolution))
                .map(ValueOrError::value)
                .filter(Constant.class::isInstance)
                .findAny()
                .isPresent();
    }
}
