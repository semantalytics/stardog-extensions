package com.semantalytics.stardog.kibble.lang;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.github.pemistahl.lingua.api.Language;
import com.stardog.stark.Value;

import java.util.Arrays;

import static com.stardog.stark.Values.*;
import static java.util.stream.Collectors.joining;

public class DetectableLanguages extends AbstractFunction implements UserDefinedFunction {

    public DetectableLanguages() {
        super(0, LanguageVocabulary.detectableLanguages.toString());
    }

    public DetectableLanguages(final DetectableLanguages detectableLanguages) {
        super(detectableLanguages);
    }

    @Override
    protected ValueOrError internalEvaluate(Value... values) {
        return ValueOrError.General.of(literal(Arrays.stream(Language.values()).map(Language::toString).collect(joining(" ,"))));
    }

    @Override
    public Function copy() {
        return new DetectableLanguages(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}
