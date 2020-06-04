package com.semantalytics.stardog.kibble.array;

import com.complexible.common.rdf.model.ArrayLiteral;
import com.complexible.stardog.plan.filter.AbstractExpression;
import com.complexible.stardog.plan.filter.Expression;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.ValueSolution;
import com.complexible.stardog.plan.filter.expr.ValueOrError;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import com.google.common.collect.Lists;
import com.stardog.stark.Datatype;
import com.stardog.stark.Literal;

import java.util.List;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertArrayLiteral;
import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertLiteral;
import static com.stardog.stark.Values.literal;

public final class Index extends AbstractExpression implements UserDefinedFunction {

    public Index() {
        super(new Expression[0]);
    }

    public Index(final Index append) {
        super(append);
    }

    @Override
    public String getName() {
        return ArrayVocabulary.index.toString();
    }

    @Override
    public List<String> getNames() {
        return Lists.newArrayList(getName());
    }

    @Override
    public ValueOrError evaluate(final ValueSolution valueSolution) {

        if(getArgs().size() == 2) {
           final ValueOrError firstArgValue = getFirstArg().evaluate(valueSolution);
           if(!firstArgValue.isError() && assertArrayLiteral(firstArgValue.value())) {
               final long[] ids = ((ArrayLiteral)firstArgValue.value()).getValues();
               final ValueOrError secondArgValueOrError = getSecondArg().evaluate(valueSolution);
               if(!secondArgValueOrError.isError() && assertLiteral(secondArgValueOrError.value())) {
                   final Literal secondArgLiteral = (Literal)secondArgValueOrError.value();
                   final int index;

                   if (secondArgLiteral.datatypeIRI() == ArrayVocabulary.ordinalDatatype.iri) {
                       index = Integer.parseInt(secondArgLiteral.label()) - 1;
                   } else if (secondArgLiteral.datatypeIRI() == ArrayVocabulary.offsetDatatype.iri) {
                       index = Integer.parseInt(secondArgLiteral.label());
                   } else {
                       return ValueOrError.Error;
                   }

                   if (index >= 0 && index < ids.length) {
                       return ValueOrError.General.of(valueSolution.getDictionary().getValue(ids[index]));
                   } else {
                       return ValueOrError.Error;
                   }
               } else {
                   return ValueOrError.Error;
               }
           } else {
               return ValueOrError.Error;
           }
        } else {
            return ValueOrError.Error;
        }
    }

    @Override
    public Index copy() {
        return new Index(this);
    }

    @Override
    public void accept(final ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    @Override
    public String toString() {
        return ArrayVocabulary.index.name();
    }
}

