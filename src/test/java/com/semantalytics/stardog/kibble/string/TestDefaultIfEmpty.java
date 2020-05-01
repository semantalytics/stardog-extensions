package com.semantalytics.stardog.kibble.string;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class TestDefaultIfEmpty extends AbstractStardogTest {

    @Test
    public void testEmpty() {
   
       final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:defaultIfEmpty(\"\", \"Stardog\") AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final Value aValue = aResult.next().value("result").get();

                assertThat((((Literal)aValue).label())).isEqualTo("Stardog");
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testNotEmpty() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                "select ?result where { bind(string:defaultIfEmpty(\"z\", \"Stardog\") AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final Value aValue = aResult.next().value("result").get();

            assertThat((((Literal)aValue).label())).isEqualTo("z");
            assertThat(aResult.hasNext()).isFalse();
        }
    }

    @Test
    public void testBlank() {
    
       final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:defaultIfEmpty(\" \", \"Stardog\") as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final Value aValue = aResult.next().value("result").get();

                assertThat((((Literal)aValue).label())).isEqualTo(" ");
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testTooFewArgs() {

       final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:defaultIfEmpty(\"one\") as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testTooManyArgs() {

       final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:defaultIfEmpty(\"one\", \"two\", \"three\") as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }
    
    @Test
    public void testWrongTypeFirstArg() {
       
       final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:defaultIfEmpty(\"one\", 2) as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testWrongTypeSecondArg() {
     
       final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:defaultIfEmpty(\"one\", 2) as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }
}
