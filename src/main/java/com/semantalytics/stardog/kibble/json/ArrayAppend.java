package com.semantalytics.stardog.kibble.json;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.io.IOException;

import static com.stardog.stark.Values.literal;

public class ArrayAppend extends AbstractFunction implements UserDefinedFunction {

    private ObjectMapper objectMapper = new ObjectMapper();

    public ArrayAppend() {
        super(1, JsonVocabulary.arrayAppend.toString());
    }

    public ArrayAppend(final ArrayAppend arrayAppend) {
        super(arrayAppend);
    }

    @Override
    protected ValueOrError internalEvaluate(Value... values) {
        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {
            final String json = ((Literal) values[0]).label();
            final String arrayValue = ((Literal) values[1]).label();
            final ArrayNode arrayNode;
            try {
                arrayNode = objectMapper.readValue(json, ArrayNode.class);
            } catch (IOException e) {
                return ValueOrError.Error;

            }
            arrayNode.add(arrayValue);
            return ValueOrError.General.of(literal(arrayNode.toString()));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public ArrayAppend copy() {
        return new ArrayAppend(this);
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}
