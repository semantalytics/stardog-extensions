package com.semantalytics.stardog.kibble.json;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.internal.filter.ValueNode;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import static com.stardog.stark.Values.literal;

public class Path extends AbstractFunction implements UserDefinedFunction {

    public Path() {
        super(2, JsonVocabulary.path.toString());
    }

    public Path(final Path path) {
        super(path);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {
            final String json = ((Literal)values[0]).label();
            final String path = ((Literal)values[1]).label();
            ValueNode.JsonNode jsonNode = JsonPath.parse(json).read(path, ValueNode.JsonNode.class);
            return ValueOrError.General.of(literal(jsonNode.toString()));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Path copy() {
        return new Path(this);
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }
}
