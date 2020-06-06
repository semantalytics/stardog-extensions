package com.semantalytics.stardog.kibble.util;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;

public class Proquint extends AbstractFunction implements UserDefinedFunction {

    public Proquint() {
        super(2, UtilVocabulary.proquint.stringValue());
    }

    private Proquint(final Proquint proquint) {
        super(proquint);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        return null;
    }

    @Override
    public Proquint copy() {
        return new Proquint(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return UtilVocabulary.proquint.name();
    }
}
