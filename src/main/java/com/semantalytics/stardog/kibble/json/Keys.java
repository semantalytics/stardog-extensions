package com.semantalytics.stardog.kibble.json;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.index.dictionary.MappingDictionary;
import com.complexible.stardog.plan.filter.AbstractExpression;
import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.ValueSolution;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import com.google.gson.Gson;
import com.stardog.stark.Literal;
import com.stardog.stark.Values;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;

public class Keys extends AbstractExpression implements UserDefinedFunction {

    private final ObjectMapper objectMapper = new ObjectMapper();

    Keys() {
        super(new Expression[0]);
    }

    Keys(final Keys keys) {
        super(keys);
    }

    @Override
    public String getName() {
        return JsonVocabulary.keys.stringValue();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public Function copy() {
        return new Keys(this);
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public ValueOrError evaluate(ValueSolution valueSolution) {
        if(getArgs().size() == 1) {
            final ValueOrError jsonStringValueOrError = getArgs().get(0).evaluate(valueSolution);
            if (!jsonStringValueOrError.isError() && assertStringLiteral(jsonStringValueOrError.value())) {
                final String jsonString = ((Literal) jsonStringValueOrError.value()).label();
                final JsonNode jsonNode;
                try {
                    jsonNode = objectMapper.readTree(jsonString);
                } catch (IOException e) {
                    return ValueOrError.Error;
                }
                if(jsonNode.isObject()) {
                    final MappingDictionary mappingDictionary = valueSolution.getDictionary();
                    long[] ids = Streams.stream(jsonNode.fieldNames()).map(Values::literal).mapToLong(mappingDictionary::add).toArray();

                    return ValueOrError.General.of(new ArrayLiteral(ids));
                } else {
                    return ValueOrError.Error ;
                }
            } else {
                return ValueOrError.Error;
            }
        }
        return null;
    }
}
