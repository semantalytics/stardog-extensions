package com.semantalytics.stardog.kibble.function;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.index.dictionary.MappingDictionary;
import com.complexible.stardog.plan.filter.*;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.FunctionDefinition;
import com.complexible.stardog.plan.filter.functions.FunctionRegistry;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.api.client.util.Value;
import com.google.common.collect.Lists;
import com.stardog.stark.IRI;
import com.stardog.stark.Literal;
import org.apache.calcite.linq4j.tree.ConstantExpression;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertArrayLiteral;
import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertLiteral;
import static java.util.stream.Collectors.toList;

public final class Map extends AbstractExpression implements UserDefinedFunction {

    protected Map() {
        super(new Expression[0]);
    }

    private Map(final Map map) {
        super(map);
    }

    @Override
    public String getName() {
        return FunctionVocabulary.map.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public Map copy() {
        return new Map(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public ValueOrError evaluate(final ValueSolution valueSolution) {

        if(getArgs().size() == 2) {
            final ValueOrError firstArgValueOrError = getFirstArg().evaluate(valueSolution);
            if(firstArgValueOrError.isError()) {
                return ValueOrError.Error;
            } else {
                final String functionIri;

                if (assertLiteral(firstArgValueOrError.value())) {
                    functionIri = ((Literal) firstArgValueOrError.value()).label();
                } else if (firstArgValueOrError.value() instanceof IRI) {
                    functionIri = firstArgValueOrError.toString();
                } else {
                    return ValueOrError.Error;
                }

                final ValueOrError secondArgValueOrError = getSecondArg().evaluate(valueSolution);
                if(secondArgValueOrError.isError()) {
                    return ValueOrError.Error;
                } else {
                    final ArrayLiteral elements;

                    if (assertArrayLiteral(secondArgValueOrError.value())) {
                        elements = (ArrayLiteral) secondArgValueOrError.value();
                        MappingDictionary dictionary = valueSolution.getDictionary();

                        try {
                            List<ValueOrError> elementResults = Arrays.stream(elements.getValues())
                                    .mapToObj(dictionary::getValue)
                                    .map(e -> FunctionRegistry.Instance.get(functionIri, Lists.newArrayList(Expressions.constant(e)), null)
                                            .evaluate(valueSolution))
                                    .collect(toList());

                            if(elementResults.stream().anyMatch(ValueOrError::isError)) {
                                return ValueOrError.Error;
                            } else {
                                long[] elementResultIds = elementResults.stream().map(ValueOrError::value).map(dictionary::add).mapToLong(Long::longValue).toArray();
                                return ValueOrError.General.of(new ArrayLiteral(elementResultIds));
                            }
                        } catch(UnsupportedOperationException e) {
                            return ValueOrError.Error;
                        }
                    } else {
                        return ValueOrError.Error;
                    }
                }
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public String toString() {
        return FunctionVocabulary.map.toString();
    }
}