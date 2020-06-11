package com.semantalytics.stardog.kibble.geo.geohash;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.assertj.core.api.Assertions.assertThat;

public class TestEncode extends AbstractStardogTest {

    private final String sparqlPrefix = GeoHashVocabulary.sparqlPrefix("geohash");

   
    @Test
    public void testOneArg() {
    
        final String aQuery = sparqlPrefix +
                    "select ?result where { bind(geohash:encode(48.669, -4.329) AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).withFailMessage("Should have a result").hasNext();

                final String aValue = ((Literal)aResult.next().value("result").get()).label();

                assertThat(aValue).isEqualTo("gbsuv7ztqzpt");
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testTooFewArgs() {

        final String aQuery = sparqlPrefix +
                    "select ?result where { bind(geohash:encode(1) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext();

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).withFailMessage("Should have no bindings").isEmpty();
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = sparqlPrefix +
                    "select ?result where { bind(geohash:encode(1, 2, 3) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
       
                assertThat(aResult).hasNext();

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).withFailMessage("Should have no bindings").isEmpty();
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testWrongTypeFirstArg() {

        final String aQuery = sparqlPrefix +
                    "select ?result where { bind(geohash:encode(\"one\", 2) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
     
                assertThat(aResult).hasNext();

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).withFailMessage("Should have no bindings").isEmpty();
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testWrongTypeSecondArg() {

        final String aQuery = sparqlPrefix +
                    "select ?result where { bind(geohash:encode(1, \"two\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
   
                assertThat(aResult).hasNext();

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).withFailMessage("Should have no bindings").isEmpty();
                assertThat(aResult).isExhausted();
            }
    }
}
