package com.semantalytics.stardog.kibble.array;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.index.dictionary.MappingDictionary;
import com.complexible.stardog.plan.filter.*;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Lists;
import com.stardog.stark.Literal;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertArrayLiteral;
import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertLiteral;

public final class RemoveAll extends AbstractExpression implements UserDefinedFunction {

    public RemoveAll() {
        super(new Expression[0]);
    }

    public RemoveAll(final RemoveAll removeAll) {
        super(removeAll);
    }

    @Override
    public String getName() {
        return ArrayVocabulary.removeAll.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public ValueOrError evaluate(final ValueSolution valueSolution) {

        if(getArgs().size() == 2) {
            final ValueOrError firstArgValueOrError = getFirstArg().evaluate(valueSolution);
            if (!firstArgValueOrError.isError() && assertArrayLiteral(firstArgValueOrError.value())) {
                final ArrayLiteral arrayLiteral = (ArrayLiteral) firstArgValueOrError.value();

                final ValueOrError secondArgValueOrError = getSecondArg().evaluate(valueSolution);
                if (!secondArgValueOrError.isError() && assertLiteral(secondArgValueOrError.value()) && EvalUtil.isNumericDatatype(((Literal) secondArgValueOrError.value()).datatype())) {

                    final MappingDictionary mappingDictionary = valueSolution.getDictionary();

                    return ValueOrError.General.of(new ArrayLiteral(ArrayUtils.removeAllOccurences(arrayLiteral.getValues(), mappingDictionary.add(secondArgValueOrError.value()))));
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
    public RemoveAll copy() {
        return new RemoveAll(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.removeAll.name();
    }
}

