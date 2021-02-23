package com.semantalytics.stardog.kibble.lang;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.index.dictionary.MappingDictionary;
import com.complexible.stardog.plan.filter.AbstractExpression;
import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.ValueSolution;
import com.complexible.stardog.plan.filter.expr.Constant;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.github.pemistahl.lingua.api.Language;
import com.github.pemistahl.lingua.api.LanguageDetector;
import com.github.pemistahl.lingua.api.LanguageDetectorBuilder;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.stardog.stark.Literal;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;
import static com.semantalytics.stardog.kibble.lang.AbstractDetector.assertAllConstant;
import static com.semantalytics.stardog.kibble.lang.AbstractDetector.assertNoErrors;
import static com.stardog.stark.Values.literal;
import static java.util.stream.Collectors.toSet;

public class ScoreFromAllExcept extends AbstractExpression implements UserDefinedFunction {

    private LanguageDetector detector;

    public ScoreFromAllExcept() {
        super(new Expression[0]);
    }

    public ScoreFromAllExcept(final ScoreFromAllExcept detectFrom) {
        super(detectFrom);
    }

    @Override
    public String getName() {
        return LanguageVocabulary.scoreFromAllExcept.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public Function copy() {
        return new ScoreFromAllExcept(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public ValueOrError evaluate(ValueSolution valueSolution) {
        if (getArgs().size() >= 2) {
            final ValueOrError firstArgValueOrError = getArgs().get(0).evaluate(valueSolution);
            if (!firstArgValueOrError.isError() && assertStringLiteral(firstArgValueOrError.value())) {
                final String text = ((Literal) firstArgValueOrError.value()).label();
                    if (assertNoErrors(getArgs().stream().skip(1), valueSolution) && assertAllConstant(getArgs().stream().skip(1), valueSolution)) {
                        final Set<Language> excludeLangs = getArgs().stream()
                                .skip(1).map(e -> e.evaluate(valueSolution))
                                .map(ValueOrError::value)
                                .map(Literal.class::cast)
                                .map(Literal::label)
                                .map(String::toUpperCase)
                                .map(Language::valueOf)
                                .collect(toSet());

                        try {
                            detector = detectorCache.get(excludeLangs);
                        } catch (ExecutionException e) {
                            return ValueOrError.Error;
                        }
                }

                final MappingDictionary mappingDictionary = valueSolution.getDictionary();

                final long[] scoredLanguage = detector.computeLanguageConfidenceValues(text).entrySet().stream().mapToLong(e ->
                        mappingDictionary.add(new ArrayLiteral(new long[]{mappingDictionary.add(literal(e.getKey().getIsoCode639_1().toString())), mappingDictionary.add(literal(e.getValue().doubleValue()))}))
                ).toArray();

                return ValueOrError.General.of(new ArrayLiteral(scoredLanguage));

            } else {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    protected LoadingCache<Set<Language>, LanguageDetector> detectorCache = CacheBuilder.newBuilder().softValues().build(
            new CacheLoader<Set<Language>, LanguageDetector>() {

                @Override
                public LanguageDetector load(Set<Language> languages) {
                    return LanguageDetectorBuilder.fromAllLanguagesWithout(languages.toArray(new Language[0])).build();
                }
            });
}
