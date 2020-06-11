package com.semantalytics.stardog.kibble.jdbc;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.index.dictionary.MappingDictionary;
import com.complexible.stardog.plan.filter.AbstractExpression;
import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.ValueSolution;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Lists;
import com.stardog.stark.Values;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Collections;
import java.util.List;

public class Drivers extends AbstractExpression implements UserDefinedFunction {

    public Drivers() {
        super(new Expression[0]);
    }

    public Drivers(final Drivers drivers) {
        super(drivers);
    }

    @Override
    public String getName() {
        return JdbcVocabulary.drivers.iri.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public Drivers copy() {
        return new Drivers(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public ValueOrError evaluate(final ValueSolution valueSolution) {
        final MappingDictionary mappingDictionary = valueSolution.getDictionary();
        final long[] ids = Collections.list(DriverManager.getDrivers())
                                      .stream()
                                      .map(Driver::toString)
                                      .map(Values::literal)
                                      .mapToLong(mappingDictionary::add)
                                      .toArray();
        return ValueOrError.General.of(new ArrayLiteral(ids));
    }

    @Override
    public String toString() {
        return JdbcVocabulary.drivers.toString();
    }
}
