package com.semantalytics.stardog.kibble.jdbc;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Value;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class AutoCommitFailureClosesAllResultSets extends AbstractFunction implements UserDefinedFunction {

    public AutoCommitFailureClosesAllResultSets() {
        super(1, JdbcVocabulary.autoCommitFailurewClosesAllResultSets.stringValue());
    }

    public AutoCommitFailureClosesAllResultSets(final AutoCommitFailureClosesAllResultSets executeDouble) {
        super(executeDouble);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        final Optional<String> iri = JdbcUtils.fromLiteralOrIRI(values[0]);
        if (iri.isPresent()) {
            try (final Connection connection = DriverManager.getConnection(iri.get())) {
                final DatabaseMetaData metadata = connection.getMetaData();
                return ValueOrError.Boolean.of(metadata.autoCommitFailureClosesAllResultSets());
            } catch (SQLException e) {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public AutoCommitFailureClosesAllResultSets copy() {
        return new AutoCommitFailureClosesAllResultSets(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return JdbcVocabulary.autoCommitFailurewClosesAllResultSets.name();
    }
}
