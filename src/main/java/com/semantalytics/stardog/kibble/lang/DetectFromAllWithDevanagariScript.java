package com.semantalytics.stardog.kibble.lang;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.github.pemistahl.lingua.api.Language;
import com.github.pemistahl.lingua.api.LanguageDetector;
import com.github.pemistahl.lingua.api.LanguageDetectorBuilder;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.Values;

import java.lang.ref.SoftReference;

public class DetectFromAllWithDevanagariScript extends AbstractFunction implements UserDefinedFunction {

    private static SoftReference<LanguageDetector> detector;

    public DetectFromAllWithDevanagariScript() {
        super(1, LanguageVocabulary.detectFromAllWithDevanagariScript.toString());
    }

    public DetectFromAllWithDevanagariScript(final DetectFromAllWithDevanagariScript detect) {
        super(detect);
    }

    @Override
    public DetectFromAllWithDevanagariScript copy() {
        return new DetectFromAllWithDevanagariScript(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public ValueOrError internalEvaluate(final Value... values) {
        if (getArgs().size() == 1) {
            if (assertStringLiteral(values[0])) {
                final String text = ((Literal) values[0]).label();
                if (detector == null) {
                        detector = new SoftReference<>(LanguageDetectorBuilder.fromAllLanguagesWithDevanagariScript().build());
                }
                final Language detectedLanguage = detector.get().detectLanguageOf(text);
                Literal textWithDetectedLang = Values.literal(text, detectedLanguage.getIsoCode639_1().toString());
                return ValueOrError.General.of(textWithDetectedLang);

            } else {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }
}
