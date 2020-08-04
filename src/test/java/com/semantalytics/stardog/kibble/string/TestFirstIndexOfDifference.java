package com.semantalytics.stardog.kibble.string;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class TestFirstIndexOfDifference extends AbstractStardogTest {

    @Test
    public void test() {

            final String aQuery = "prefix string: <" + StringVocabulary.NS + "> " +
                    "select ?result where { bind(string:indexOfDifference(\"Stardog\", \"Starman\") AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());
                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final int aValue = Literal.intValue((Literal) aResult.next().value("result").get());

                assertEquals(4, aValue);
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testEmptyString() {
       

            final String aQuery = "prefix string: <" + StringVocabulary.NS + "> " +
                    "select ?result where { bind(string:indexOfDifference(\"\", \"\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());
                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final int aValue = Literal.intValue((Literal) aResult.next().value("result").get());

                assertEquals(-1, aValue);
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testTooFewArgs() {

            final String aQuery = "prefix string: <" + StringVocabulary.NS + "> " +
                    "select ?result where { bind(string:indexOfDifference(\"one\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testTooManyArgs() {

            final String aQuery = "prefix string: <" + StringVocabulary.NS + "> " +
                    "select ?result where { bind(string:indexOfDifference(\"one\", \"two\", \"three\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testWrongTypeFirstArg() {

        final String aQuery = "prefix string: <" + StringVocabulary.NS + "> " +
                "select ?result where { bind(string:indexOfDifference(1, \"two\") as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted();
        }
    }

    @Test
    public void testWrongTypeSecondArg() {
       
            final String aQuery = "prefix string: <" + StringVocabulary.NS + "> " +
                    "select ?result where { bind(string:indexOfDifference(\"one\", 2) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted();
            }
    }
}

