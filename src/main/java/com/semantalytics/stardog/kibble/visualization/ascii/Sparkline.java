package com.semantalytics.stardog.kibble.visualization.ascii;

import com.complexible.stardog.plan.filter.EvalUtil;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.Values;

import static com.stardog.stark.Values.literal;
import static java.util.stream.Collectors.toList;

public final class Sparkline extends AbstractFunction implements StringFunction {

    private static final Spark spark = new Spark();
    private final StringBuffer stringBuffer = new StringBuffer();

    public Sparkline() {
        super(Range.atLeast(1), AsciiVisualizationVocabulary.sparkline.stringValue());
    }

    private Sparkline(final Sparkline spark) {
        super(spark);
        stringBuffer.setLength(0);
        stringBuffer.append(spark.stringBuffer.toString());
    }

    @Override
    protected ValueOrError internalEvaluate(final Value... values) {

        for(final Value value : values) {
            assertLiteral(value);
            final Literal literal = ((Literal)value);
            if (literal.datatype() != null && EvalUtil.isNumericDatatype(literal.datatype())) {
                stringBuffer.append(spark.internalEvaluate(value).stringValue());
            } else if (EvalUtil.isStringLiteral(literal)) {
                for (final Literal l : ((Literal)value).label().chars().mapToObj(Character::getNumericValue).map(Values::literal).collect(toList())) {
                    stringBuffer.append(spark.internalEvaluate(l).stringValue());
                }
            } else {
                return ValueOrError.Error;
            }
        }
        return ValueOrError.General.of(literal(stringBuffer.toString()));
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public Sparkline copy() {
        return new Sparkline(this);
    }

    @Override
    public String toString() {
        return AsciiVisualizationVocabulary.sparkline.name();
    }

}
