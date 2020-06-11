package com.semantalytics.stardog.kibble.string;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import java.util.Optional;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class TestChomp extends AbstractStardogTest {

    @Test
    public void testChomp() {
     
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:chomp(\"Stardog\\n\") AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final Optional<Value> aPossibleValue = aResult.next().value("result");
                assertThat(aPossibleValue).isPresent();
                final Value aValue = aPossibleValue.get();
                assertThat(assertStringLiteral(aValue));
                assertThat(((Literal)aValue).label()).isEqualTo("Stardog");
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testEmptyString() {
      
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:chomp(\"\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final Optional<Value> aPossibleValue = aResult.next().value("result");
                assertThat(aPossibleValue).isPresent();
                final Value aValue = aPossibleValue.get();
                assertThat(assertStringLiteral(aValue));

                assertThat(((Literal)aValue).label()).isEqualTo("");
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testTooFewArgs() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:chomp() as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:chomp(\"one\", \"two\") as ?result) }";

           try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

               assertThat(aBindingSet).isEmpty();
               assertThat(aResult).isExhausted();
           }
    }

    @Test
    public void testWrongTypeFirstArg() {
      
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:chomp(1) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted();
            }
    }
}
