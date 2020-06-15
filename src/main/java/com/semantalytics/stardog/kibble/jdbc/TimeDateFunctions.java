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

import static com.stardog.stark.Values.literal;

public class TimeDateFunctions extends AbstractFunction implements UserDefinedFunction {

    public TimeDateFunctions() {
        super(1, JdbcVocabulary.timeDateFunctions.stringValue());
    }

    public TimeDateFunctions(final TimeDateFunctions executeDouble) {
        super(executeDouble);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        final Optional<String> iri = JdbcUtils.fromLiteralOrIRI(values[0]);
        if (iri.isPresent()) {
            try (final Connection connection = DriverManager.getConnection(iri.get())) {
                final DatabaseMetaData metadata = connection.getMetaData();
                return ValueOrError.General.of(literal(metadata.getTimeDateFunctions()));
            } catch (SQLException e) {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public TimeDateFunctions copy() {
        return new TimeDateFunctions(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return JdbcVocabulary.timeDateFunctions.name();
    }

}
