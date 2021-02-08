package com.semantalytics.stardog.kibble.string;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class TestInitials extends AbstractStardogTest {

    @Test
    public void testInitialsOneArg() {

            final String aQuery = "prefix string: <" + StringVocabulary.NS + "> " +
                    "select ?result where { bind(string:initials(\"Stardog graph database\") AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final Value aValue = aResult.next().value("result").get();

                assertThat(((Literal)aValue).label()).isEqualTo("Sgd");
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testInitialsTwoArg() {

        final String aQuery = "prefix string: <" + StringVocabulary.NS + "> " +
                "select ?result where { bind(string:initials(\"Stardog,graph,database\", \",\") AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final Value aValue = aResult.next().value("result").get();

            assertThat(((Literal)aValue).label()).isEqualTo("Sgd");
            assertThat(aResult.hasNext()).isFalse();
        }
    }

    @Test
    public void testEmptyString() {
       

            final String aQuery = "prefix string: <" + StringVocabulary.NS + "> " +
                    "select ?result where { bind(string:initials(\"\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final Value aValue = aResult.next().value("result").get();

                assertThat(((Literal)aValue).label()).isEqualTo("");
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testTooFewArgs() {

            final String aQuery = "prefix string: <" + StringVocabulary.NS + "> " +
                    "select ?result where { bind(string:initials() as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testTooManyArgs() {

       
            final String aQuery = "prefix string: <" + StringVocabulary.NS + "> " +
                    "select ?result where { bind(string:initials(\"one\", \"two\", \"three\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testWrongTypeFirstArg() {

        final String aQuery = "prefix string: <" + StringVocabulary.NS + "> " +
                "select ?result where { bind(string:initials(1, \"two\") as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet.size()).isZero();
            assertThat(aResult.hasNext()).isFalse();
        }
    }

    @Test
    public void testWrongTypeSecondArg() {
       
            final String aQuery = "prefix string: <" + StringVocabulary.NS + "> " +
                    "select ?result where { bind(string:initials(\"one\", 2) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }
}
