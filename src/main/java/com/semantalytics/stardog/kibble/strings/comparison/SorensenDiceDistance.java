package com.semantalytics.stardog.kibble.strings.comparison;

import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.Constant;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.Function;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;

public class SorensenDiceDistance extends AbstractFunction implements StringFunction {

    private info.debatty.java.stringsimilarity.SorensenDice sorensenDice;

    protected SorensenDiceDistance() {
        super(Range.closed(2, 3), StringMetricVocabulary.sorensenDiceDistance.stringValue());
    }

    private SorensenDiceDistance(final SorensenDiceDistance sorensenDiceDistance) {
        super(sorensenDiceDistance);
        this.sorensenDice = sorensenDiceDistance.sorensenDice;
    }

    @Override
    public void initialize() {
        sorensenDice = null;
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        if(assertStringLiteral(values[0]) && assertStringLiteral(values[1])) {

            final String firstString = ((Literal)values[0]).label();
            final String secondString = ((Literal)values[1]).label();

            if(sorensenDice == null) {
                if (values.length == 3) {
                    if(!(getThirdArg() instanceof Constant)) {
                        return ValueOrError.Error;
                    }

                    if(assertNumericLiteral(values[2])) {
                        final int n = Literal.intValue((Literal)values[2]);
                        sorensenDice = new info.debatty.java.stringsimilarity.SorensenDice(n);
                    } else {
                        return ValueOrError.Error;
                    }
                } else {
                    sorensenDice = new info.debatty.java.stringsimilarity.SorensenDice();
                }
            }
            return ValueOrError.Double.of(sorensenDice.distance(firstString, secondString));
        } else {
            return ValueOrError.Error;
        }
    }

    public Function copy() {
        return new SorensenDiceDistance(this);
    }

    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return StringMetricVocabulary.sorensenDiceDistance.name();
    }
}
