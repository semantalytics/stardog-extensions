package com.semantalytics.stardog.kibble.nlp;

import com.complexible.stardog.plan.filter.ExpressionEvaluationException;
import com.complexible.stardog.plan.filter.ExpressionVisitor;
import com.complexible.stardog.plan.filter.functions.AbstractFunction;
import com.complexible.stardog.plan.filter.functions.UserDefinedFunction;
import de.unihd.dbs.heideltime.standalone.DocumentType;
import de.unihd.dbs.heideltime.standalone.HeidelTimeStandalone;
import de.unihd.dbs.heideltime.standalone.OutputType;
import de.unihd.dbs.heideltime.standalone.POSTagger;
import de.unihd.dbs.heideltime.standalone.exceptions.DocumentCreationTimeMissingException;
import de.unihd.dbs.uima.annotator.heideltime.resources.Language;
import org.openrdf.model.Value;

import static com.complexible.common.rdf.model.Values.literal;

public class HeidelTime extends AbstractFunction implements UserDefinedFunction {

    private static final HeidelTimeStandalone heidelTime = new HeidelTimeStandalone(Language.ENGLISH,
                                          DocumentType.COLLOQUIAL,
                                          OutputType.TIMEML,
                                        "path/to/config.props",
                                          POSTagger.TREETAGGER, true);

        protected HeidelTime() {
            super(1, NlpVocabulary.heidelTime.stringValue());
        }

        public HeidelTime(final HeidelTime heidelTime) {
            super(heidelTime);
        }

        @Override
        protected Value internalEvaluate(final Value... values) throws ExpressionEvaluationException {

            final String document = assertStringLiteral(values[0]).stringValue();
            final String processedDocument;

            try {
                processedDocument = heidelTime.process(document);
            } catch(DocumentCreationTimeMissingException e) {
                    throw new ExpressionEvaluationException(e);
            }

            return literal(processedDocument);
        }

        @Override
        public HeidelTime copy() {
            return new HeidelTime(this);
        }

        @Override
        public void accept(final ExpressionVisitor expressionVisitor) {
            expressionVisitor.visit(this);
        }

        @Override
        public String toString() {
            return NlpVocabulary.heidelTime.name();
        }
}
