package com.semantalytics.stardog.kibble.jdbc;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.IRI;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class IsDriverRegistered extends AbstractFunction implements UserDefinedFunction {

    public IsDriverRegistered() {
        super(1, JdbcVocabulary.isDriverRegistered.stringValue());
    }

    public IsDriverRegistered(final IsDriverRegistered isDriverRegistered) {
        super(isDriverRegistered);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        final Optional<String> iri = JdbcUtils.fromLiteralOrIRI(values[0]);

        if(iri.isPresent()) {
            try {
                DriverManager.getDriver(iri.get());
            } catch (SQLException e) {
                return ValueOrError.Boolean.False;
            }
            return ValueOrError.Boolean.True;
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public IsDriverRegistered copy() {
        return new IsDriverRegistered(this);
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
