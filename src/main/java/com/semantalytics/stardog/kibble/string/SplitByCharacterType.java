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
import com.stardog.stark.Value;
import com.stardog.stark.Values;

import java.util.Arrays;
import java.util.List;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;
import static org.apache.commons.lang3.StringUtils.*;

public final class SplitByCharacterType extends AbstractExpression implements StringFunction {

    protected SplitByCharacterType() {
        super(new Expression[0]);
    }

    private SplitByCharacterType(final SplitByCharacterType splitByCharacterType) {
        super(splitByCharacterType);
    }

    @Override
    public String getName() {
        return StringVocabulary.splitByCharacterType.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public SplitByCharacterType copy() {
        return new SplitByCharacterType(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public ValueOrError evaluate(ValueSolution valueSolution) {
        if(getArgs().size() == 1) {
            ValueOrError valueOrErrorString = getArgs().get(0).evaluate(valueSolution);
            if(!valueOrErrorString.isError() && assertStringLiteral(valueOrErrorString.value())) {
                final String string = ((Literal)valueOrErrorString.value()).label();
                final String[] splits = (splitByCharacterType(string));
                MappingDictionary mappingDictionary = valueSolution.getDictionary();
                long[] ids = Arrays.stream(splits).map(Values::literal).mapToLong(mappingDictionary::add).toArray();
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
        return StringVocabulary.splitByCharacterType.toString();
    }
}
