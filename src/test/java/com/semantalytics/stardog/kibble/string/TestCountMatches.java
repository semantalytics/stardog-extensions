package com.semantalytics.stardog.kibble.string;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class TestCountMatches extends AbstractStardogTest {

    @Test
    public void testNonZeroMatches() {
    
       final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:countMatches(\"Stardog\", \"t\") AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final int aValue = Literal.intValue((Literal)aResult.next().value("result").get());

                assertThat(aValue).isEqualTo(1);
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testZeroMatches() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                "select ?result where { bind(string:countMatches(\"Stardog\", \"z\") AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final int aValue = Literal.intValue((Literal)aResult.next().value("result").get());

            assertThat(aValue).isEqualTo(0);
            assertThat(aResult).isExhausted();
        }
    }

    @Test
    public void testEmptyString() {
   
       final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:countMatches(\"\", \"z\") as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final int aValue = Literal.intValue((Literal)aResult.next().value("result").get());

                assertThat(aValue).isEqualTo(0);
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testTooFewArgs() {

       final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:countMatches(\"one\") as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testTooManyArgs() {

       final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:countMatches(\"one\", \"two\", \"three\") as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testWrongTypeFirstArg() {
      
       final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:countMatches(1, \"two\") as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testWrongTypeSecondArg() {
       
       final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:countMatches(\"one\", 2) as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted();
            }
    }
}
