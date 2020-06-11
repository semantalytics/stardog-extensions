package com.semantalytics.stardog.kibble.geo.geohash;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class TestHashContains extends AbstractStardogTest {

    private final String sparqlPrefix = GeoHashVocabulary.sparqlPrefix("geohash");

    @Test
    public void testThreeArgTrue() {
    
        final String aQuery = sparqlPrefix +
                    "select ?result where { bind(geohash:hashContains(\"gbsuv7ztqzpt\", 48.669, -4.329) AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final boolean aValue = Literal.booleanValue((Literal)aResult.next().get("result"));

                assertEquals(true, aValue);
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testThreeArgFalse() {

        final String aQuery = sparqlPrefix +
                "select ?result where { bind(geohash:hashContains(\"gbsuv7ztqzpt\", 0.0, 0.0) AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final boolean aValue = (Literal.booleanValue((Literal)aResult.next().value("result").get()));

            assertEquals(false, aValue);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testTooFewArgs() {

        final String aQuery = sparqlPrefix +
                    "select ?result where { bind(geohash:hashContains(\"one\", 2.0) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).withFailMessage("Should have no bindings").isEmpty();
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = sparqlPrefix +
                    "select ?result where { bind(geohash:hashContains(\"one\", 2, 3, 4) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
       
                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).withFailMessage("Should have no bindings").isEmpty();
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testWrongTypeFirstArg() {

        final String aQuery = sparqlPrefix +
                    "select ?result where { bind(geohash:hashContains(1, 2.0, 3.0) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
     
                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).withFailMessage("Should have no bindings").isEmpty();
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testWrongTypeSecondArg() {

        final String aQuery = sparqlPrefix +
                "select ?result where { bind(geohash:hashContains(\"one\", \"two\", 3.0) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).withFailMessage("Should have no bindings").isEmpty();
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testWrongTypeThirdArg() {

        final String aQuery = sparqlPrefix +
                "select ?result where { bind(geohash:hashContains(\"one\", 2.0, \"three\") as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).withFailMessage("Should have no bindings").isEmpty();
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }
}
