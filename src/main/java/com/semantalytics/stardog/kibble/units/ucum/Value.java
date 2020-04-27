package com.semantalytics.stardog.kibble.units.ucum;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;

import javax.xml.datatype.XMLGregorianCalendar;

import static com.complexible.common.rdf.model.Values.literal;

public class Value extends AbstractFunction implements UserDefinedFunction {

        public Value() {
            super(1, UcumVocabulary.value.stringValue());
        }

        public Value(final Value value) {
            super(value);
        }

        @Override
        protected Value internalEvaluate(final org.openrdf.model.Value... values) throws ExpressionEvaluationException {

            return null;
        }

        @Override
        public Value copy() {
            return new Value(this);
        }

        @Override
        public void accept(final ExpressionVisitor expressionVisitor) {
            expressionVisitor.visit(this);
        }

        @Override
        public String toString() {
            return DateVocabulary.value.name();
        }
}

