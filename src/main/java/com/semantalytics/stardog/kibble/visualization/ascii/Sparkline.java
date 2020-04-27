package com.semantalytics.stardog.kibble.visualization.ascii;

import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.plan.filter.EvalUtil;
import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Range;
import org.openrdf.model.Literal;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;
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
    protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

        for(final Value value : values) {
            final Literal literal = assertLiteral(value);
            if (literal.getDatatype() != null && EvalUtil.isNumericDatatype(literal.getDatatype())) {
                stringBuffer.append(spark.internalEvaluate(value).stringValue());
            } else if (EvalUtil.isStringLiteral(literal)) {
                for (final Literal l : value.stringValue().chars().mapToObj(Character::getNumericValue).map(Values::literal).collect(toList())) {
                    stringBuffer.append(spark.internalEvaluate(l).stringValue());
                }
            } else {
                throw new ExpressionEvaluationException("Arguments to function spark must be either a string or an int");
            }
        }
        return literal(stringBuffer.toString());
    }

    public Value evaluate(final Value... values) throws ExpressionEvaluationException {
        this.assertRequiredArgs(values.length);
        return this.internalEvaluate(values);
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
