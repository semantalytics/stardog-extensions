package com.semantalytics.stardog.kibble.string.emoji;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.index.dictionary.MappingDictionary;
import com.complexible.stardog.plan.filter.AbstractExpression;
import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.ValueSolution;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Lists;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.Values;
import emoji4j.Emoji;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;
import static emoji4j.EmojiUtils.*;
import static java.util.stream.Collectors.*;

public final class Aliases extends AbstractExpression implements StringFunction {

    public Aliases() {
        super(new Expression[0]);
    }

    public Aliases(final List<Expression> expressions) {
        super(expressions);
    }

    private Aliases(final Aliases theExpr) {
        super(theExpr);
    }

    @Override
    public ValueOrError evaluate(final ValueSolution valueSolution) {
        if(getArgs().size() == 1) {
            ValueOrError valueOrErrorString = getArgs().get(0).evaluate(valueSolution);
            if(!valueOrErrorString.isError() && assertStringLiteral(valueOrErrorString.value())) {
                final String string = ((Literal) valueOrErrorString.value()).label();
                Optional<Emoji> maybeEmoji = Optional.ofNullable(getEmoji(string));
                if(maybeEmoji.isPresent()) {
                    List<String> aliases = maybeEmoji.get().getAliases();
                    MappingDictionary mappingDictionary = valueSolution.getDictionary();
                    long[] ids = aliases.stream().map(Values::literal).mapToLong(mappingDictionary::add).toArray();
                    return ValueOrError.General.of(new ArrayLiteral(ids));
                } else {
                    return ValueOrError.General.of(new ArrayLiteral(new long[] {}));
                }
            } else {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(new String[] { this.getName() });
    }

    @Override
    public Aliases copy() {
        return new Aliases(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String getName() {
        return EmojiVocabulary.aliases.toString();
    }
}
