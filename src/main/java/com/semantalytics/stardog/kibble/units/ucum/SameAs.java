package com.semantalytics.stardog.kibble.units.ucum;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

import javax.xml.datatype.XMLGregorianCalendar;

import static com.complexible.common.rdf.model.Values.literal;

public class SameAs extends AbstractFunction implements UserDefinedFunction {

        public SameAs() {
            super(1, UcumVocabulary.sameAs.stringValue());
        }

        public SameAs(final SameAs sameAs) {
            super(sameAs);
        }

        @Override
        protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

            return null;
        }

        @Override
        public SameAs copy() {
            return new SameAs(this);
        }

        @Override
        public void accept(final ExpressionVisitor expressionVisitor) {
            expressionVisitor.visit(this);
        }

        @Override
        public String toString() {
            return DateVocabulary.sameAs.name();
        }
}
