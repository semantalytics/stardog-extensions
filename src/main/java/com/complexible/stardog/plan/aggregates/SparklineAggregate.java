package com.complexible.stardog.plan.aggregates;

import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.google.common.base.Preconditions;
import com.semantalytics.stardog.kibble.visualization.ascii.Spark;
import com.semantalytics.stardog.kibble.visualization.ascii.AsciiVisualizationVocabulary;
import org.openrdf.model.Value;

import java.util.List;

import static com.complexible.common.rdf.model.Values.literal;

public final class SparklineAggregate extends AbstractAggregate {

    private final StringBuffer stringBuffer = new StringBuffer();
    private final Spark spark = new Spark();

    protected SparklineAggregate() {
        super(AsciiVisualizationVocabulary.sparkline.stringValue());
    }

    private SparklineAggregate(final SparklineAggregate sparklineAggregate) {
        super(sparklineAggregate);
        stringBuffer.append(sparklineAggregate.stringBuffer.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setArgs(final List<Expression> args) {
        Preconditions.checkArgument(args.size() == 1, "Spark function takes only one argument, %d found.", args.size());

        super.setArgs(args);
        spark.setArgs(args);
    }

    @Override
    protected Value _getValue() throws ExpressionEvaluationException {
        return literal(stringBuffer.toString());
    }

    @Override
    protected void aggregate(final long multiplicity, final Value value, Value... otherValues) throws ExpressionEvaluationException {
        stringBuffer.append(spark.evaluate(value));
    }

    @Override
    public SparklineAggregate copy() {
        return new SparklineAggregate(this);
    }

}
