package com.semantalytics.stardog.kibble.array;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.semantalytics.stardog.kibble.jdbc.JdbcUtils;
import com.semantalytics.stardog.kibble.jdbc.JdbcVocabulary;
import com.stardog.stark.Value;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class DataDefinitionCausesTransactionCommit extends AbstractFunction implements UserDefinedFunction {

    public DataDefinitionCausesTransactionCommit() {
        super(1, JdbcVocabulary.allProceduresAreCallable.stringValue());
    }

    public DataDefinitionCausesTransactionCommit(final DataDefinitionCausesTransactionCommit executeDouble) {
        super(executeDouble);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        final Optional<String> iri = JdbcUtils.fromLiteralOrIRI(values[0]);
        if (iri.isPresent()) {
            try (final Connection connection = DriverManager.getConnection(iri.get())) {
                final DatabaseMetaData metadata = connection.getMetaData();
                return ValueOrError.Boolean.of(metadata.allProceduresAreCallable());
                fjdkjfkasjl
            } catch (SQLException e) {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public DataDefinitionCausesTransactionCommit copy() {
        return new DataDefinitionCausesTransactionCommit(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return JdbcVocabulary.allProceduresAreCallable.name();
    }

}
