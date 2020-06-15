package com.semantalytics.stardog.kibble.jdbc;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class DriverPropertyInfo extends AbstractFunction implements UserDefinedFunction {

    public DriverPropertyInfo() {
        super(1, JdbcVocabulary.driverPropertyInfo.stringValue());
    }

    public DriverPropertyInfo(final DriverPropertyInfo executeDouble) {
        super(executeDouble);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        final Optional<String> iri = JdbcUtils.fromLiteralOrIRI(values[0]);
        assertStringLiteral(values[0]);
        final String url = ((Literal)values[0]).label();
        try {
            DriverManager.getDriver(url);
        } catch (SQLException e) {
            return ValueOrError.Boolean.False;
        }
        return ValueOrError.Boolean.True;
    }

    @Override
    public DriverPropertyInfo copy() {
        return new DriverPropertyInfo(this);
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
