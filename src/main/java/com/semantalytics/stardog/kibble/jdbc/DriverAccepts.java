package com.semantalytics.stardog.kibble.jdbc;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Value;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class DriverAccepts extends AbstractFunction implements UserDefinedFunction {

    public DriverAccepts() {
        super(1, JdbcVocabulary.driverAccepts.stringValue());
    }

    public DriverAccepts(final DriverAccepts executeDouble) {
        super(executeDouble);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        final Optional<String> iri = JdbcUtils.fromLiteralOrIRI(values[0]);
        try {
            DriverManager.getDriver(iri.toString()).acceptsURL(iri.toString());
        } catch (SQLException e) {
            return ValueOrError.Boolean.False;
        }
        return ValueOrError.Boolean.True;
    }

    @Override
    public DriverAccepts copy() {
        return new DriverAccepts(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return JdbcVocabulary.isDriverRegistered.name();
    }
}
