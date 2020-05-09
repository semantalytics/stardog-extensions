package com.semantalytics.stardog.kibble.string;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.stardog.stark.Values.literal;
import static java.util.stream.Collectors.*;
import static org.apache.commons.lang3.StringUtils.*;

public final class AppendIfMissingIgnoreCase extends AbstractFunction implements StringFunction {

    protected AppendIfMissingIgnoreCase() {
        super(Range.atLeast(2), StringVocabulary.appendIfMissingIgnoreCase.toString());
    }

    private AppendIfMissingIgnoreCase(final AppendIfMissingIgnoreCase appendIfMissingIgnoreCase) {
        super(appendIfMissingIgnoreCase);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if (!assertStringLiteral(values[0]) || !assertStringLiteral(values[1])) {
            return ValueOrError.Error;
        }

        final String string = ((Literal) values[0]).label();
        final String suffix = ((Literal) values[1]).label();

        List<ValueOrError> suffixValues = Arrays.stream(values).skip(2).map(v -> {
            if (assertStringLiteral(v)) {
                return ValueOrError.General.of(v);
            } else {
                return ValueOrError.Error;
            }
        }).collect(toList());

        if(suffixValues.contains(ValueOrError.Error)) {
            return ValueOrError.Error;
        } else {
            List<String> suffixes = suffixValues.stream().map(v -> (Literal)v).map(Literal::label).collect(toCollection(ArrayList::new));
            return ValueOrError.General.of(literal(appendIfMissingIgnoreCase(string, suffix, suffixes.toArray(new String[suffixes.size()]))));
        }
    }

    @Override
    public AppendIfMissingIgnoreCase copy() {
        return new AppendIfMissingIgnoreCase(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringVocabulary.appendIfMissingIgnoreCase.name();
    }
}
