package com.semantalytics.stardog.kibble.string;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class TestSplitByCharacterType extends AbstractStardogTest {

    @Test
    public void test() {
      
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { unnest(string:splitByCharacterType(\"Stardog8graph8database\") AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext().withFailMessage("Should have a result");
                final Value value1 = aResult.next().value("result").get();
                assertThat(((Literal)value1).label()).isEqualTo("S");

                assertThat(aResult).hasNext().withFailMessage("Should have a result");
                final Value value2 = aResult.next().value("result").get();
                assertThat(((Literal)value2).label()).isEqualTo("tardog");

                assertThat(aResult).hasNext().withFailMessage("Should have a result");
                final Value value3 = aResult.next().value("result").get();
                assertThat(((Literal)value3).label()).isEqualTo("8");

                assertThat(aResult).hasNext().withFailMessage("Should have a result");
                final Value value4 = aResult.next().value("result").get();
                assertThat(((Literal)value4).label()).isEqualTo("graph");

                assertThat(aResult).hasNext().withFailMessage("Should have a result");
                final Value value5 = aResult.next().value("result").get();
                assertThat(((Literal)value5).label()).isEqualTo("8");

                assertThat(aResult).hasNext().withFailMessage("Should have a result");
                final Value value6 = aResult.next().value("result").get();
                assertThat(((Literal)value6).label()).isEqualTo("database");

                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testEmptyString() {
      
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { unnest(string:splitByCharacterType(\"\") as ?result) }";

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
                    "select ?result where { bind(string:splitByCharacterType() as ?result) }";

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
                    "select ?result where { bind(string:splitByCharacterType(\"one\", \"two\") as ?result) }";

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
                    "select ?result where { bind(string:splitByCharacterType(1) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }
}
