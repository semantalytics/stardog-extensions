
package com.semantalytics.stardog.kibble.units.ucum;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import org.openrdf.model.Value;

import javax.xml.datatype.XMLGregorianCalendar;

import static com.complexible.common.rdf.model.Values.literal;

public class Units extends AbstractFunction implements UserDefinedFunction {

        public Units() {
            super(1, UcumVocabulary.units.stringValue());
        }

        public Units(final Units units) {
            super(units);
        }

        @Override
        protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

            return null;
        }

        @Override
        public Units copy() {
            return new Units(this);
        }

        @Override
        public void accept(final ExpressionVisitor expressionVisitor) {
            expressionVisitor.visit(this);
        }

        @Override
        public String toString() {
            return DateVocabulary.units.name();
        }
}
