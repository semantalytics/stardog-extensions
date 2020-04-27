package com.semantalytics.stardog.kibble.console;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import org.junit.Test;
import org.openrdf.query.TupleQueryResult;

import static org.junit.Assert.*;

public class TestBlinkSlow extends AbstractStardogTest {

    private static final String consoleSparqlPrefix = ConsoleVocabulary.sparqlPrefix("console");

    @Test
    public void testNoArg() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:blinkSlow() AS ?result) }";

        try (final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final String aValue = aResult.next().getValue("result").stringValue();

            assertEquals("\u001b[5m", aValue);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testOneArg() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:blinkSlow(\"Stardog\") AS ?result) }";

        try (final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final String aValue = aResult.next().getValue("result").stringValue();

            assertEquals("\u001b[5mStardog\u001b[25m", aValue);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testTwoArg() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:blinkSlow(\"one\", \"two\") AS ?result) }";

        try (final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final String aValue = aResult.next().getValue("result").stringValue();

            assertEquals("\u001b[5monetwo\u001b[25m", aValue);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testEmptyString() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:blinkSlow(\"\") as ?result) }";

        try (final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final String aValue = aResult.next().getValue("result").stringValue();

            assertEquals("\u001b[5m\u001b[25m", aValue);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testOneArgInteger() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:blinkSlow(1) AS ?result) }";

        try (final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final String aValue = aResult.next().getValue("result").stringValue();

            assertEquals("\u001b[5m1\u001b[25m", aValue);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }
}
