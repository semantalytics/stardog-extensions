package com.semantalytics.stardog.kibble.console;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import org.junit.Test;
import org.openrdf.query.TupleQueryResult;

import static org.junit.Assert.*;

public class TestConceal extends AbstractStardogTest {

    private static final String consoleSparqlPrefix = ConsoleVocabulary.sparqlPrefix("console");

    @Test
    public void testNoArg() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:conceal() AS ?result) }";

        try (final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final String aValue = aResult.next().getValue("result").stringValue();

            assertEquals("\u001b[8m", aValue);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testOneArg() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:conceal(\"Stardog\") AS ?result) }";

        try (final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final String aValue = aResult.next().getValue("result").stringValue();

            assertEquals("\u001b[8mStardog\u001b[28m", aValue);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testTwoArg() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:conceal(\"one\", \"two\") AS ?result) }";

        try (final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final String aValue = aResult.next().getValue("result").stringValue();

            assertEquals("\u001b[8monetwo\u001b[28m", aValue);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testEmptyString() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:conceal(\"\") as ?result) }";

        try (final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final String aValue = aResult.next().getValue("result").stringValue();

            assertEquals("\u001b[8m\u001b[28m", aValue);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testOneArgInteger() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:conceal(1) AS ?result) }";

        try (final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final String aValue = aResult.next().getValue("result").stringValue();

            assertEquals("\u001b[8m1\u001b[28m", aValue);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }
}
