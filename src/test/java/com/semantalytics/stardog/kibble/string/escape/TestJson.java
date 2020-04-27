package com.semantalytics.stardog.kibble.string.escape;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import org.junit.Test;
import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQueryResult;

import static org.junit.Assert.*;

public class TestJson extends AbstractStardogTest {

    private static final String sparqlPrefix = EscapeVocabulary.sparqlPrefix("escape");

    @Test
    public void testOneArg() {

        final String aQuery = sparqlPrefix +
                "select ?result where { bind(escape:json(\"He didn't say, \\\"Stop!\\\"\") AS ?result) }";

        try (final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final String aValue = aResult.next().getValue("result").stringValue();

            assertEquals("He didn't say, \\\"Stop!\\\"", aValue);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }
    
    @Test
    public void testEmptyString() {

        final String aQuery = sparqlPrefix +
                "select ?result where { bind(escape:json(\"\") as ?result) }";

        try(final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final String aValue = aResult.next().getValue("result").stringValue();

            assertEquals("", aValue);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testTooFewArgs() {

        final String aQuery = sparqlPrefix +
                "select ?result where { bind(escape:json() as ?result) }";

        try(final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = sparqlPrefix +
                "select ?result where { bind(escape:json(\"one\", \"two\") as ?result) }";

        try(final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testWrongTypeFirstArg() {

        final String aQuery = sparqlPrefix +
                "select ?result where { bind(escape:json(1) as ?result) }";

        try(final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }
}
