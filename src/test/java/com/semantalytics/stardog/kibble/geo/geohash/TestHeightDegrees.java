package com.semantalytics.stardog.kibble.geo.geohash;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class TestHeightDegrees extends AbstractStardogTest {

    private final String sparqlPrefix = GeoHashVocabulary.sparqlPrefix("geohash");

   
    @Test
    public void testOneArg() {
    
        final String aQuery = sparqlPrefix +
                    "select ?result where { bind(geohash:heightDegrees(2) AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final double aValue = Literal.doubleValue((Literal)aResult.next().value("result").get());

                assertEquals(5.625, aValue, 0.0001);
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testTooFewArgs() {

        final String aQuery = sparqlPrefix +
                    "select ?result where { bind(geohash:heightDegrees() as ?result) }";

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
                    "select ?result where { bind(geohash:heightDegrees(1, 2) as ?result) }";

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
                    "select ?result where { bind(geohash:heightDegrees(\"one\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
     
                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).withFailMessage("Should have no bindings").isEmpty();
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }
}
