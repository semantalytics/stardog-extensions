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

public class OthersDeletesAreVisible extends AbstractFunction implements UserDefinedFunction {

    public OthersDeletesAreVisible() {
        super(1, JdbcVocabulary.nullsAreSortedLow.stringValue());
    }

    public OthersDeletesAreVisible(final OthersDeletesAreVisible executeDouble) {
        super(executeDouble);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        final Optional<String> iri = JdbcUtils.fromLiteralOrIRI(values[0]);
        if (iri.isPresent() && assertLiteral(values[1])) {
            try (final Connection connection = DriverManager.getConnection(iri.get())) {
                final DatabaseMetaData metadata = connection.getMetaData();
                int type = Literal.intValue((Literal)values[1]);
                return ValueOrError.Boolean.of(metadata.othersDeletesAreVisible(type));
            } catch (SQLException e) {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public OthersDeletesAreVisible copy() {
        return new OthersDeletesAreVisible(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return JdbcVocabulary.nullsAreSortedLow.name();
    }
}
