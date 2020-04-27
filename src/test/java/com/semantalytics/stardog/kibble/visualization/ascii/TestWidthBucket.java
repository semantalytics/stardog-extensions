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

public class TestWidthBucket extends AbstractStardogTest {

    private static final String sparqlPrefix = AsciiVisualizationVocabulary.sparqlPrefix("vis");

    @Test
    public void testFourArg() {

        final String aQuery = sparqlPrefix +
                " select ?result where { bind(vis:widthBucket(1.5, 0, 10, 10) as ?result) }";

        try(final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final Value aValue = aResult.next().getValue("result");

            assertTrue(aValue instanceof Literal);

            final int aLiteralValue = ((Literal)aValue).intValue();

            assertEquals(1, aLiteralValue);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testOnBucketBoundryLower() {

        final String aQuery = sparqlPrefix +
                " select ?result where { bind(vis:widthBucket(0, 0, 10, 10) as ?result) }";

        try(final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final Value aValue = aResult.next().getValue("result");

            assertTrue(aValue instanceof Literal);

            final int aLiteralValue = ((Literal)aValue).intValue();

            assertEquals(0, aLiteralValue);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testOnBucketBoundryUpper() {

        final String aQuery = sparqlPrefix +
                " select ?result where { bind(vis:widthBucket(1, 0, 10, 10) as ?result) }";

        try(final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final Value aValue = aResult.next().getValue("result");

            assertTrue(aValue instanceof Literal);

            final int aLiteralValue = ((Literal)aValue).intValue();

            assertEquals(1, aLiteralValue);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testTooFewAargs() {

        final String aQuery = sparqlPrefix +
                " select ?result where { bind(vis:widthBucket(1, 0, 10) as ?result) }";

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
                " select ?result where { bind(vis:widthBucket(1, 0, 10, 10, 10) as ?result) }";

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
                " select ?result where { bind(vis:widthBucket(\"one\", 0, 10, 10) as ?result) }";

        try(final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testSecondArgWrongType() {

        final String aQuery = sparqlPrefix +
                " select ?result where { bind(vis:widthBucket(1, \"zero\", 10, 10) as ?result) }";

        try(final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testThirdArgWrongType() {

        final String aQuery = sparqlPrefix +
                " select ?result where { bind(vis:widthBucket(1, 0, \"ten\", 10) as ?result) }";

        try(final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testFourthArgWrongType() {

        final String aQuery = sparqlPrefix +
                " select ?result where { bind(vis:widthBucket(1, 0, 10, \"ten\") as ?result) }";

        try(final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testMinGreaterThanMax() {

        final String aQuery = sparqlPrefix +
                " select ?result where { bind(vis:widthBucket(1, 10, 0, 10) as ?result) }";

        try(final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }
}
