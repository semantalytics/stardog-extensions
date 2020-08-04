package com.semantalytics.stardog.kibble.geo.geohash;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class TestLongitude extends AbstractStardogTest {

    private final String sparqlPrefix = GeoHashVocabulary.sparqlPrefix("geohash");

   
    @Test
    public void testOneArg() {
    
        final String aQuery = sparqlPrefix +
                    "select ?result where { bind(geohash:longitude(\"gbsuv7ztgzpt\") AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final double aValue = Literal.doubleValue((Literal)aResult.next().value("result").get());

                assertEquals(-4.32912876829505, aValue, 0.000001);
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testTooFewArgs() {

        final String aQuery = sparqlPrefix +
                    "select ?result where { bind(geohash:longitude() as ?result) }";

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
                    "select ?result where { bind(geohash:longitude(\"one\", \"two\") as ?result) }";

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
                    "select ?result where { bind(geohash:longitude(1) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
     
                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).withFailMessage("Should have no bindings").isEmpty();
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }
}
