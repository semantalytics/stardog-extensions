package com.semantalytics.stardog.kibble.string;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class TestSplitByWholeSeparator extends AbstractStardogTest {

    @Test
    public void testTwoArg() {
      
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { unnest(string:splitByWholeSeparator(\"Stardog-!-Graph-!-Database\", \"-!-\") AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext().withFailMessage("Should have a result");
                final Value value1 = aResult.next().value("result").get();
                assertThat(((Literal)value1).label()).isEqualTo("Stardog");

                assertThat(aResult).hasNext().withFailMessage("Should have a result");
                final Value value2 = aResult.next().value("result").get();
                assertThat(((Literal)value2).label()).isEqualTo("Graph");

                assertThat(aResult).hasNext().withFailMessage("Should have a result");
                final Value value3 = aResult.next().value("result").get();
                assertThat(((Literal)value3).label()).isEqualTo("Database");

                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testThreeArg() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                "select ?result where { unnest(string:splitByWholeSeparator(\"Stardog-!-Graph-!-Database\", \"-!-\", 2) AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Value value1 = aResult.next().value("result").get();
            assertThat(((Literal)value1).label()).isEqualTo("Stardog");

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Value value2 = aResult.next().value("result").get();
            assertThat(((Literal)value2).label()).isEqualTo("Graph-!-Database");

            assertThat(aResult).isExhausted();
        }
    }


    @Test
    public void testEmptyString() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { unnest(string:splitByWholeSeparator(\"\", \"\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testTooFewArgs() {
      
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:splitByWholeSeparator(\"one\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
         
                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:splitByWholeSeparator(\"one\", \"two\", \"three\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testWrongTypeFirstArg() {
       
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:splitByWholeSeparator(1, \"two\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testWrongTypeSecondArg() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                "select ?result where { bind(string:splitByWholeSeparator(\"one\", 2) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet.size()).isZero();
            assertThat(aResult.hasNext()).isFalse();
        }
    }
}
