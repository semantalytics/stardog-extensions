package com.semantalytics.stardog.kibble.jdbc;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Value;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class IsDriverJdbcCompliant extends AbstractFunction implements UserDefinedFunction {

    public IsDriverJdbcCompliant() {
        super(1, JdbcVocabulary.isDriverJdbcCompliant.stringValue());
    }

    public IsDriverJdbcCompliant(final IsDriverJdbcCompliant isDriverJdbcCompliant) {
        super(isDriverJdbcCompliant);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        final Optional<String> iri = JdbcUtils.fromLiteralOrIRI(values[0]);
        if (iri.isPresent()) {
            try {
                return ValueOrError.Boolean.of(DriverManager.getDriver(iri.toString()).jdbcCompliant());
            } catch (SQLException e) {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public IsDriverJdbcCompliant copy() {
        return new IsDriverJdbcCompliant(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return JdbcVocabulary.isDriverJdbcCompliant.name();
    }
}
