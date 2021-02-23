package com.semantalytics.stardog.kibble.lang;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.index.dictionary.MappingDictionary;
import com.complexible.stardog.plan.filter.AbstractExpression;
import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.ValueSolution;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.github.pemistahl.lingua.api.LanguageDetector;
import com.github.pemistahl.lingua.api.LanguageDetectorBuilder;
import com.google.common.collect.Lists;
import com.stardog.stark.Literal;

import java.lang.ref.SoftReference;
import java.util.List;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;
import static com.stardog.stark.Values.literal;

public class ScoreFromAllWithArabicScript extends AbstractExpression implements UserDefinedFunction {

    private static SoftReference<LanguageDetector> detector;

    public ScoreFromAllWithArabicScript() {
        super(new Expression[0]);
    }

    public ScoreFromAllWithArabicScript(final ScoreFromAllWithArabicScript detectFrom) {
        super(detectFrom);
    }

    @Override
    public String getName() {
        return LanguageVocabulary.scoreFromAllWithArabicScript.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public Function copy() {
        return new ScoreFromAllWithArabicScript(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public ValueOrError evaluate(ValueSolution valueSolution) {
        if (getArgs().size() == 1) {
            final ValueOrError firstArgValue = getArgs().get(0).evaluate(valueSolution);
            if (!firstArgValue.isError() && assertStringLiteral(firstArgValue.value())) {
                final String text = ((Literal) firstArgValue.value()).label();
                if (detector == null) {
                    detector = new SoftReference<>(LanguageDetectorBuilder.fromAllLanguagesWithArabicScript().build());
                }

                final MappingDictionary mappingDictionary = valueSolution.getDictionary();

                final long[] scoredLanguage = detector.get().computeLanguageConfidenceValues(text).entrySet().stream().mapToLong(e ->
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
}
