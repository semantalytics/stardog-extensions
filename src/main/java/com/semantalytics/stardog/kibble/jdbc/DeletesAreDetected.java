package com.semantalytics.stardog.kibble.jdbc;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.sql.*;
import java.util.Optional;

public class DeletesAreDetected extends AbstractFunction implements UserDefinedFunction {

    public DeletesAreDetected() {
        super(2, JdbcVocabulary.deletesAreDetected.stringValue());
    }

    public DeletesAreDetected(final DeletesAreDetected executeDouble) {
        super(executeDouble);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        final Optional<String> iri = JdbcUtils.fromLiteralOrIRI(values[0]);
        if (iri.isPresent() && assertLiteral(values[1])) {
            try (final Connection connection = DriverManager.getConnection(iri.get())) {
                int type = Literal.intValue((Literal)values[1]);
                final DatabaseMetaData metadata = connection.getMetaData();
                //TODO use something other than an int
                return ValueOrError.Boolean.of(metadata.deletesAreDetected(type));
            } catch (SQLException e) {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public DeletesAreDetected copy() {
        return new DeletesAreDetected(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return JdbcVocabulary.deletesAreDetected.name();
    }
}
