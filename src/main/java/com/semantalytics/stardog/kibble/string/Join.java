package com.semantalytics.stardog.kibble.string;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.plan.filter.AbstractExpression;
import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.ValueSolution;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Lists;
import com.stardog.stark.Literal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertArrayLiteral;
import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;
import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class Join extends AbstractExpression implements StringFunction {

    public Join() {
        super(new Expression[0]);
    }

    public Join(List<Expression> expressions) {
        super(expressions);
    }

    private Join(final Join theExpr) {
        super(theExpr);
    }

    @Override
    public String getName() {
        return StringVocabulary.join.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(new String[]{this.getName()});
    }

    @Override
    public Join copy() {
        return new Join(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public ValueOrError evaluate(ValueSolution theValueSolution) {
        if(!this.getArgs().isEmpty()) {
            List<String> pieces = new ArrayList<>();
            for (final Expression arg : this.getArgs()) {
                ValueOrError valueOrError = arg.evaluate(theValueSolution);
                if (!valueOrError.isError()) {
                        if (assertArrayLiteral(valueOrError.value())) {
                            ArrayLiteral array = ((ArrayLiteral) valueOrError.value());
                            List<String> values = Arrays.stream(array.getValues()).mapToObj(d -> theValueSolution.getDictionary().getValue(d)).map(v -> ((Literal) v).label()).collect(Collectors.toList());
                            pieces.addAll(values);
                        } else if (assertStringLiteral(valueOrError.value())) {
                            pieces.add(((Literal) valueOrError.value()).label());
                        } else {
                            return ValueOrError.Error;
                        }
                } else {
                    return ValueOrError.Error;
                }
            }
            return ValueOrError.General.of(literal(join(pieces.toArray())));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public String toString() {
        return getName();
    }
}
