package com.semantalytics.stardog.kibble.string;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.index.dictionary.MappingDictionary;
import com.complexible.stardog.plan.filter.AbstractExpression;
import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.ValueSolution;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Lists;
import com.stardog.stark.Literal;
import com.stardog.stark.Values;

import java.util.List;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;

public final class CharactersOf extends AbstractExpression implements StringFunction {

    protected CharactersOf() {
        super(new Expression[0]);
    }

    private CharactersOf(final CharactersOf charactersOf) {
        super(charactersOf);
    }


    @Override
    public String getName() {
        return StringVocabulary.charactersOf.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public CharactersOf copy() {
        return new CharactersOf(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public ValueOrError evaluate(ValueSolution valueSolution) {

        if (getArgs().size() == 1) {
            final ValueOrError stringValueOrError = getFirstArg().evaluate(valueSolution);
            if (!stringValueOrError.isError() && assertStringLiteral(stringValueOrError.value())) {
                String string = ((Literal) stringValueOrError.value()).label();
                MappingDictionary mappingDictionary = valueSolution.getDictionary();
                long[] ids = Lists.charactersOf(string).stream().map(c -> mappingDictionary.add(Values.literal(c.toString()))).mapToLong(Long::longValue).toArray();
                return ValueOrError.General.of(new ArrayLiteral(ids));
            } else {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public String toString() {
        return StringVocabulary.charactersOf.toString();
    }
}
