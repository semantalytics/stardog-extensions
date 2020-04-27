package com.semantalytics.stardog.kibble.jdbc;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Value;

import java.sql.*;
import java.util.Optional;

public class DatabaseMajorVersion extends AbstractFunction implements UserDefinedFunction {

    public DatabaseMajorVersion() {
        super(1, JdbcVocabulary.databaseMajorVersion.stringValue());
    }

    public DatabaseMajorVersion(final DatabaseMajorVersion databaseMajorVersion) {
        super(databaseMajorVersion);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        final Optional<String> iri = JdbcUtils.fromLiteralOrIRI(values[0]);
        if(iri.isPresent()) {
            try (final Connection connection = DriverManager.getConnection(iri.get())) {
                final DatabaseMetaData metaData = connection.getMetaData();
                return ValueOrError.Int.of(metaData.getDatabaseMajorVersion());
            } catch (SQLException e) {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public DatabaseMajorVersion copy() {
        return new DatabaseMajorVersion(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return JdbcVocabulary.databaseMajorVersion.name();
    }
}
