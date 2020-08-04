package com.semantalytics.stardog.kibble.json;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.util.Arrays;

import static com.stardog.stark.Values.literal;

public class Array extends AbstractFunction implements UserDefinedFunction {

    private ObjectMapper objectMapper = new ObjectMapper();

    public Array() {
        super(Range.atLeast(1), JsonVocabulary.array.toString());
    }

    public Array(final Array array) {
        super(array);
    }

    @Override
    protected ValueOrError internalEvaluate(Value... values) {
        if(Arrays.stream(values).allMatch(AbstractFunction::assertLiteral)) {
            ArrayNode arrayNode = objectMapper.createArrayNode();
            Arrays.stream(values).map(Literal.class::cast).map(Literal::label).forEach(arrayNode::add);
            return ValueOrError.General.of(literal(arrayNode.toString()));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Array copy() {
        return new Array(this);
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}
