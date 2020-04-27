package com.semantalytics.stardog.kibble.visualization.ascii;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import org.junit.Test;
import org.openrdf.model.Literal;
import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQueryResult;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestSparkline extends AbstractStardogTest {

    private static final String sparqlPrefix = AsciiVisualizationVocabulary.sparqlPrefix("vis");

    @Test
    public void testOneArgInt() {

        final String aQuery = sparqlPrefix +
                " select ?result where { bind(vis:sparkline(1) as ?result) }";

        try(final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final Value aValue = aResult.next().getValue("result");

            assertTrue(aValue instanceof Literal);

            assertEquals("\u2582", aValue.stringValue());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testTwoArgInt() {

        final String aQuery = sparqlPrefix +
                " select ?result where { bind(vis:sparkline(1, 2) as ?result) }";

        try(final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final Value aValue = aResult.next().getValue("result");

            assertTrue(aValue instanceof Literal);

            assertEquals("\u2582\u2583", aValue.stringValue());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testOneArgString() {

        final String aQuery = sparqlPrefix +
                " select ?result where { bind(vis:sparkline(\"1\") as ?result) }";

        try(final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final Value aValue = aResult.next().getValue("result");

            assertTrue(aValue instanceof Literal);

            final String aLiteralValue = ((Literal)aValue).stringValue();

            assertEquals("\u2582", aLiteralValue);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testTwoArgString() {

        final String aQuery = sparqlPrefix +
                " select ?result where { bind(vis:sparkline(\"1\", \"2\") as ?result) }";

        try(final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final Value aValue = aResult.next().getValue("result");

            assertTrue(aValue instanceof Literal);

            final String aLiteralValue = ((Literal)aValue).stringValue();

            assertEquals("\u2582\u2583", aLiteralValue);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testTwoMixedArgs() {

        final String aQuery = sparqlPrefix +
                " select ?result where { bind(vis:sparkline(\"1\", 2) as ?result) }";

        try(final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final Value aValue = aResult.next().getValue("result");

            assertTrue(aValue instanceof Literal);

            final String aLiteralValue = ((Literal)aValue).stringValue();

            assertEquals("\u2582\u2583", aLiteralValue);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testIndexTooSmall() {

        final String aQuery = sparqlPrefix +
                " select ?result where { bind(vis:sparkline(-1) as ?result) }";

        try(final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testIndexTooLarge() {

        final String aQuery = sparqlPrefix +
                " select ?result where { bind(vis:sparkline(8) as ?result) }";

        try(final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testTooFewAargs() {

        final String aQuery = sparqlPrefix +
                " select ?result where { bind(vis:sparkline() as ?result) }";

        try(final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testTooManyAargs() {

        final String aQuery = sparqlPrefix +
                " select ?result where { bind(vis:widthBucket(1, 2) as ?result) }";

        try(final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testFirstArgWrongType() {

        final String aQuery = sparqlPrefix +
                " select ?result where { bind(vis:widthBucket(7.7) as ?result) }";

        try(final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }
}
