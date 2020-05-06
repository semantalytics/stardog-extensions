package com.semantalytics.stardog.kibble.string;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class TestSplit extends AbstractStardogTest {

    @Test
    public void testOneArg() {
      
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { unnest(string:split(\"Stardog graph database\") AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext().withFailMessage("Should have a result");
                final Value firstValue = aResult.next().value("result").get();
                assertThat(((Literal)firstValue).label()).isEqualTo("Stardog");

                assertThat(aResult).hasNext().withFailMessage("Should have a result");
                final Value secondValue = aResult.next().value("result").get();
                assertThat(((Literal)secondValue).label()).isEqualTo("graph");

                assertThat(aResult).hasNext().withFailMessage("Should have a result");
                final Value thirdValue = aResult.next().value("result").get();
                assertThat(((Literal)thirdValue).label()).isEqualTo("database");

                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testTwoArg() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                "select ?result where { unnest(string:split(\"Stardog,graph,database\", \",\") AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Value firstValue = aResult.next().value("result").get();
            assertThat(((Literal)firstValue).label()).isEqualTo("Stardog");

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Value secondValue = aResult.next().value("result").get();
            assertThat(((Literal)secondValue).label()).isEqualTo("graph");

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Value thirdValue = aResult.next().value("result").get();
            assertThat(((Literal)thirdValue).label()).isEqualTo("database");

            assertThat(aResult).isExhausted();
        }
    }

    @Test
    public void testThreeArg() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                "select ?result where { unnest(string:split(\"Stardog,graph,database\", \",\", 2) AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Value firstValue = aResult.next().value("result").get();
            assertThat(((Literal)firstValue).label()).isEqualTo("Stardog");

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Value secondValue = aResult.next().value("result").get();
            assertThat(((Literal)secondValue).label()).isEqualTo("graph,database");

            assertThat(aResult).isExhausted();
        }
    }

    @Test
    public void testEmptyString() {
      
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { unnest(string:split(\"\", \"\", 10) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
           
                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testTooFewArgs() {
      
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:split() as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
         
                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:split(\"one\", \"two\", 3, \"four\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testWrongTypeFirstArg() {
       
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:split(1, \"two\", 3) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testWrongTypeSecondArg() {
     
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:split(\"one\", 2, 3) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testWrongTypeThirdArg() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                "select ?result where { bind(string:split(\"one\", \"two\", \"three\") as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted();
        }
    }
}
