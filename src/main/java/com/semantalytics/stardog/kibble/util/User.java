package com.semantalytics.stardog.kibble.util;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Value;
import org.apache.shiro.SecurityUtils;

import static com.stardog.stark.Values.literal;

public final class User extends AbstractFunction implements UserDefinedFunction {

    public User() {
        super(0, UtilVocabulary.user.stringValue());
    }

    private User(final User user) {
        super(user);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(SecurityUtils.getSubject() != null) {
            return ValueOrError.General.of(literal(SecurityUtils.getSubject().getPrincipal().toString()));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Function copy() {
        return new User(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return UtilVocabulary.user.name();
    }
}
