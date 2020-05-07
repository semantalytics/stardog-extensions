package com.semantalytics.stardog.kibble.file;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.docs.BitesVocabulary;
import com.complexible.stardog.docs.db.BitesDbModule;
import com.complexible.stardog.index.dictionary.MappingDictionary;
import com.complexible.stardog.plan.filter.AbstractExpression;
import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.ValueSolution;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.string.StringFunction;
import com.google.common.collect.Lists;
import com.stardog.stark.Literal;
import com.stardog.stark.Values;

import java.util.Arrays;
import java.util.List;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertIntegerLiteral;
import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;
import static com.stardog.stark.Values.literal;
import static org.apache.commons.lang3.StringUtils.*;

public final class Diff extends AbstractExpression implements StringFunction {

    protected Diff() {
        super(new Expression[0]);
    }

    private Diff(final Diff diff) {
        super(diff);
    }


    @Override
    public String getName() {
        return DiffVocabulary.diff.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public Diff copy() {
        return new Diff(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public ValueOrError evaluate(ValueSolution valueSolution) {

         

        final int max;
        final String separator;
        final String string;
        final String[] splits;

        if(getArgs().size() <= 3 && getArgs().size() >= 1) {
            final ValueOrError stringValueOrError = getArgs().get(0).evaluate(valueSolution);
            if (!stringValueOrError.isError() && assertStringLiteral(stringValueOrError.value())) {
                string = ((Literal) stringValueOrError.value()).label();
            } else {
                return ValueOrError.Error;
            }
            if (getArgs().size() >= 2) {
                final ValueOrError separatorValueOrError = getArgs().get(1).evaluate(valueSolution);
                if (!separatorValueOrError.isError() && assertStringLiteral(separatorValueOrError.value())) {
                    separator = ((Literal) separatorValueOrError.value()).label();
                } else {
                    return ValueOrError.Error;
                }
                if (getArgs().size() == 3) {
                    final ValueOrError maxValueOrError = getArgs().get(2).evaluate(valueSolution);
                    if (!maxValueOrError.isError() && assertIntegerLiteral(maxValueOrError.value())) {
                        max = Literal.intValue((Literal) maxValueOrError.value());
                    } else {
                        return ValueOrError.Error;
                    }
                    splits = split(string, separator, max);
                } else {
                    splits = split(string, separator);
                }
            } else {
                splits = split(string);
            }
        } else {
            return ValueOrError.Error;
        }

        final MappingDictionary mappingDictionary = valueSolution.getDictionary();
        long[] ids = Arrays.stream(splits).map(Values::literal).mapToLong(mappingDictionary::add).toArray();

        return ValueOrError.General.of(new ArrayLiteral(ids));
    }

    @Override
    public String toString() {
        return DiffVocabulary.diff.name();
    }
}
