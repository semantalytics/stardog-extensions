package com.semantalytics.stardog.kibble.lang;

import com.complexible.stardog.plan.filter.AbstractExpression;
import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.ValueSolution;
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
import com.stardog.stark.Values;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;
import static java.util.stream.Collectors.toSet;

public class DetectFrom extends AbstractDetector implements UserDefinedFunction {

    public DetectFrom() {
        super(new Expression[0]);
    }

    public DetectFrom(final DetectFrom detectFrom) {
        super(detectFrom);
    }

    @Override
    public String getName() {
        return LanguageVocabulary.detectFrom.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public Function copy() {
        return new DetectFrom(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }


    @Override
    public ValueOrError evaluate(ValueSolution valueSolution) {
        if (getArgs().size() >= 3) {
            final ValueOrError firstArgValue = getFirstArg().evaluate(valueSolution);
            if (!firstArgValue.isError() && assertStringLiteral(firstArgValue.value())) {
                final String text = ((Literal) firstArgValue.value()).label();
                    final LanguageDetector detector;
                    //TODO this can probably be done a little better. There shouldn't be a need to make multiple passes to evaluate()
                    if (assertNoErrors(getArgs().stream().skip(1), valueSolution) && assertAllConstant(getArgs().stream().skip(1), valueSolution)) {
                        final Set<Language> includeLangs = getArgs().stream()
                                                                    .skip(1).map(e -> e.evaluate(valueSolution))
                                                                    .map(ValueOrError::value)
                                                                    .map(Literal.class::cast)
                                                                    .map(Literal::label)
                                                                    .map(String::toUpperCase)
                                                                    .map(Language::valueOf)
                                                                    .collect(toSet());

                        try {
                            detector = detectorCache.get(includeLangs);
                        } catch (ExecutionException e) {
                            return ValueOrError.Error;
                        }
                    } else {
                        return ValueOrError.Error;
                    }
                final Language detectedLanguage = detector.detectLanguageOf(text);
                final Literal textWithDetectedLang = Values.literal(text, detectedLanguage.getIsoCode639_1().toString());
                return ValueOrError.General.of(textWithDetectedLang);

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
                    return LanguageDetectorBuilder.fromLanguages(languages.toArray(new Language[0])).build();
                }
            });
}
