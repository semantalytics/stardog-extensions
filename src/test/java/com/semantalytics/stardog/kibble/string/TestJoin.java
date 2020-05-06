package com.semantalytics.stardog.kibble.string;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.assertj.core.api.Assertions.assertThat;

public class TestJoin extends AbstractStardogTest {

    @Test
    public void testArrayLiteral() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                "select ?result where { bind(string:join(string:split(\"Stardog,graph,database\", \",\")) AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final Value aValue = aResult.next().value("result").get();

            assertThat(((Literal)aValue).label()).isEqualTo("Stardoggraphdatabase");
            assertThat(aResult.hasNext()).isFalse();
        }
    }
    @Test
    public void testThreeArgs() {
       
       final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:join(\"Stardog\", \"graph\", \"database\") AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final Value aValue = aResult.next().value("result").get();

                assertThat(((Literal)aValue).label()).isEqualTo("Stardoggraphdatabase");
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testEmptyString() {
       
       final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:join(\"\", \"\") as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {
       
                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final Value aValue = aResult.next().value("result").get();

                assertThat(((Literal)aValue).label()).isEqualTo("");
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testTooFewArgs() {

       final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:join() as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testWrongTypeFirstArg() {
       
       final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:join(1, \"two\") as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testWrongTypeSecondArg() {
   
       final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:join(\"one\", 2) as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted();
            }
    }
}
