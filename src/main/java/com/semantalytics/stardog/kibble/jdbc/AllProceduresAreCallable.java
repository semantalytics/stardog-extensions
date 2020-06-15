package com.semantalytics.stardog.kibble.jdbc;

import com.complexible.stardog.StardogKernel;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.complexible.stardog.protocols.http.server.UndertowHttpServer;
import com.google.inject.Inject;
import com.stardog.stark.Value;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class AllProceduresAreCallable extends AbstractFunction implements UserDefinedFunction {

    public AllProceduresAreCallable() {
        super(1, JdbcVocabulary.allProceduresAreCallable.stringValue());
    }

    public AllProceduresAreCallable(final AllProceduresAreCallable executeDouble) {
        super(executeDouble);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        final Optional<String> iri = JdbcUtils.fromLiteralOrIRI(values[0]);
        if (iri.isPresent()) {
            try (final Connection connection = DriverManager.getConnection(iri.get())) {
                final DatabaseMetaData metadata = connection.getMetaData();
                return ValueOrError.Boolean.of(metadata.allProceduresAreCallable());
            } catch (SQLException e) {
                return ValueOrError.Error;
            }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public AllProceduresAreCallable copy() {
        return new AllProceduresAreCallable(this);
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
