package com.semantalytics.stardog.kibble.string;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class TestCompareIgnoreCase extends AbstractStardogTest {

    @Test
    public void testEqual() {
     
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:compareIgnoreCase(\"Stardog\", \"stardog\") AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final int aValue = Literal.intValue((Literal)aResult.next().value("result").get());

                assertEquals(0, aValue);
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testGreaterThan() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                "select ?result where { bind(string:compareIgnoreCase(\"star\", \"dog\") AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final int aValue = Literal.intValue((Literal)aResult.next().value("result").get());

            assertEquals(true, aValue > 0);
            assertThat(aResult.hasNext()).isFalse();
        }
    }

    @Test
    public void testLessThan() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                "select ?result where { bind(string:compareIgnoreCase(\"dog\", \"star\") AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final int aValue = Literal.intValue((Literal)aResult.next().value("result").get());

            assertEquals(true, aValue < 0);
            assertThat(aResult.hasNext()).isFalse();
        }
    }

    @Test
    public void testEmptyString() {
      
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:compareIgnoreCase(\"\", \"\") as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final int aValue = Literal.intValue((Literal)aResult.next().value("result").get());

                assertEquals(0, aValue);
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testTooFewArgs() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:compareIgnoreCase(\"one\") as ?result) }";

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
                    "select ?result where { bind(string:compareIgnoreCase(\"one\", \"two\", \"three\") as ?result) }";

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
                    "select ?result where { bind(string:compareIgnoreCase(1, \"two\") as ?result) }";

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
                    "select ?result where { bind(string:compareIgnoreCase(\"one\", 2) as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }
}
