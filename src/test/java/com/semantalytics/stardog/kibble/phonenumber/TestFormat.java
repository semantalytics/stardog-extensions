package com.semantalytics.stardog.kibble.phonenumber;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import org.junit.Test;
import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQueryResult;

import static org.junit.Assert.*;

public class TestFormat extends AbstractStardogTest {

    private final String sparqlPrefix = PhoneNumberVocabulary.sparqlPrefix("phonenumber");

    @Test
    public void testOneArg() {

        final String aQuery = sparqlPrefix +
                "select ?result where { bind(phonenumber:format(\"5551212\", \"US\") AS ?result) }";

        try (final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final String aValue = aResult.next().getValue("result").stringValue();

            assertEquals("gbsuv7ztgzpm", aValue);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testTooFewArgs() {

        final String aQuery = sparqlPrefix +
                "select ?result where { bind(phonenumber:format(\"5551212\") as ?result) }";

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
                "select ?result where { bind(phonenumber:format(\"one\", \"two\", \"three\") as ?result) }";

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
                "select ?result where { bind(phonenumber:format(1) as ?result) }";

        try(final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testWrongTypeSecondArg() {

        final String aQuery = sparqlPrefix +
                "select ?result where { bind(phonenumber:format(\"one\", 2) as ?result) }";

        try(final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }
}
