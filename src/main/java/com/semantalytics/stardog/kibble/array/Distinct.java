package com.semantalytics.stardog.kibble.array;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.stardog.stark.Value;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.stream.LongStream;

public final class Distinct extends AbstractFunction implements UserDefinedFunction {

    private static final String[] theIRIs = new String[] {ArrayVocabulary.distinct.toString()};

    protected Distinct() {
        super(1, theIRIs);
    }

    private Distinct(final Distinct distinct) {
        super(distinct);
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if (assertArrayLiteral(values[0])) {
            final long[] ids = Arrays.stream(((ArrayLiteral) values[0]).getValues()).distinct().toArray();
            long[] distinctIds = LongStream.of(ids).distinct().toArray();
            return ValueOrError.General.of(new ArrayLiteral(distinctIds));
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Distinct copy() {
        return new Distinct(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.distinct.toString();
    }
}
