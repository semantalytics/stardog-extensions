package com.semantalytics.stardog.kibble.array;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.index.dictionary.MappingDictionary;
import com.complexible.stardog.plan.filter.*;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Lists;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.util.Collections;
import java.util.List;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertLiteral;

public final class Fill extends AbstractExpression implements UserDefinedFunction {

    public Fill() {
        super(new Expression[0]);
    }

    public Fill(final Fill append) {
        super(append);
    }

    @Override
    public String getName() {
        return ArrayVocabulary.fill.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public ValueOrError evaluate(final ValueSolution valueSolution) {

        if (getArgs().size() == 2) {
            final ValueOrError firstArgValueOrError = getFirstArg().evaluate(valueSolution);
            if (!firstArgValueOrError.isError() && assertLiteral(firstArgValueOrError.value()) && EvalUtil.isNumericDatatype(((Literal) firstArgValueOrError.value()).datatype())) {
                final Value fillValue = firstArgValueOrError.value();

                final ValueOrError secondArgValueOrError = getSecondArg().evaluate(valueSolution);
                if (!secondArgValueOrError.isError() && assertLiteral(secondArgValueOrError.value()) && EvalUtil.isNumericDatatype(((Literal) secondArgValueOrError.value()).datatype())) {
                    final int size = Literal.intValue((Literal) secondArgValueOrError.value());

                    final MappingDictionary mappingDictionary = valueSolution.getDictionary();

                    final long[] ids = Collections.nCopies(size, mappingDictionary.add(fillValue)).stream().mapToLong(l -> l).toArray();

                    return ValueOrError.General.of(new ArrayLiteral(ids));
                } else {
                    return ValueOrError.Error;
                }
            } else {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Fill copy() {
        return new Fill(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.fill.toString();
    }
}

