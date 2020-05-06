package com.semantalytics.stardog.kibble.util;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.complexible.stardog.plan.filter.functions.rdfterm.Lang;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.io.LanguageHandler;
import com.stardog.stark.io.language.BCP47LanguageHandler;
import org.atteo.evo.inflector.English;

import java.util.Locale;

import static com.stardog.stark.Values.literal;

public class Pluralize extends AbstractFunction implements UserDefinedFunction {

    protected Pluralize() {
        super(1, UtilVocabulary.pluralize.stringValue());
    }

    public Pluralize(final Pluralize pluralize) {
        super(pluralize);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        //TODO not quite right. Should allow en-US as lang
        if(((Literal)values[0]).lang().isPresent()) {
            final String lang = new BCP47LanguageHandler().normalizeLanguageTag(((Literal)values[0]).lang().get());
            if (!Locale.forLanguageTag(lang).getLanguage().equals("en")) {
                return ValueOrError.Error;
            }
        }
        return ValueOrError.General.of(literal(English.plural(((Literal)values[0]).label())));
    }

    @Override
    public Function copy() {
        return new Pluralize(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return UtilVocabulary.pluralize.name();
    }
}
