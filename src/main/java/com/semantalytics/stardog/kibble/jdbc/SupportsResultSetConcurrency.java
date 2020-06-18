package com.semantalytics.stardog.kibble.jdbc;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class SupportsResultSetConcurrency extends AbstractFunction implements UserDefinedFunction {

    public SupportsResultSetConcurrency() {
        super(3, JdbcVocabulary.supportsResultSetConcurrency.stringValue());
    }

    public SupportsResultSetConcurrency(final SupportsResultSetConcurrency executeDouble) {
        super(executeDouble);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        final Optional<String> iri = JdbcUtils.fromLiteralOrIRI(values[0]);
        if (iri.isPresent() && assertIntegerLiteral(values[1]) && assertIntegerLiteral(values[2]))  {
            final int type = Literal.intValue((Literal)values[1]);
            final int concurrency = Literal.intValue((Literal)values[2]);
            try (final Connection connection = DriverManager.getConnection(iri.get())) {
                final DatabaseMetaData metadata = connection.getMetaData();
                return ValueOrError.Boolean.of(metadata.supportsResultSetConcurrency(type, concurrency));
            } catch (SQLException e) {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public SupportsResultSetConcurrency copy() {
        return new SupportsResultSetConcurrency(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return JdbcVocabulary.supportsResultSetConcurrency.name();
    }

}
