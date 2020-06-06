package com.semantalytics.stardog.kibble.util;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.github.dsw.proquint.Proquint;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;

import static com.stardog.stark.Values.literal;

public class RandomProquint extends AbstractFunction implements UserDefinedFunction {

    public RandomProquint() {
        super(1, UtilVocabulary.randomProquint.stringValue());
    }

    private RandomProquint(final RandomProquint proquint) {
        super(proquint);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {
        return ValueOrError.General.of(literal(Proquint.randomProquint()));
    }

    @Override
    public RandomProquint copy() {
        return new RandomProquint(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return UtilVocabulary.randomProquint.name();
    }
}
