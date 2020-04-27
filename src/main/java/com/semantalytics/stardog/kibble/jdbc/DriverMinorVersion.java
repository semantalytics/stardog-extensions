package com.semantalytics.stardog.kibble.jdbc;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

import static com.complexible.common.rdf.model.Values.literal;

public class DriverMinorVersion extends AbstractFunction implements UserDefinedFunction {

    public DriverMinorVersion() {
        super(1, JdbcVocabulary.driverMinorVersion.stringValue());
    }

    public DriverMinorVersion(final DriverMinorVersion executeDouble) {
        super(executeDouble);
    }

    @Override
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        final Optional<String> iri = JdbcUtils.fromLiteralOrIRI(values[0]);
        final String url = assertStringLiteral(values[0]).stringValue();
        try {
            DriverManager.getDriver(url);
        } catch (SQLException e) {
            literal(false);
        }
        return literal(true);
    }

    @Override
    public DriverMinorVersion copy() {
        return new DriverMinorVersion(this);
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
