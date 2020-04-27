package com.semantalytics.stardog.kibble.string;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class TestPrependIfMissingIgnoreCase extends AbstractStardogTest {

    @Test
    public void test() {
    
       final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:prependIfMissingIgnoreCase(\"Stardog\", \"star\") AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final Value aValue = aResult.next().value("result").get();

                assertThat(((Literal)aValue).label()).isEqualTo("Stardog");
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testEmptyString() {
    
       final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:prependIfMissingIgnoreCase(\"\", \"\") as ?result) }";

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
                    "select ?result where { bind(string:prependIfMissingIgnoreCase(\"one\") as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testWrongTypeFirstArg() {
     
       final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:prependIfMissingIgnoreCase(1, \"two\") as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testWrongTypeSecondArg() {
    
       final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:prependIfMissingIgnoreCase(\"one\", 2) as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }
}
