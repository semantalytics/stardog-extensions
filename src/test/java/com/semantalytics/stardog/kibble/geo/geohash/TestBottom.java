package com.semantalytics.stardog.kibble.geo.geohash;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestBottom extends AbstractStardogTest {

    private final String sparqlPrefix = GeoHashVocabulary.sparqlPrefix("geohash");

   
    @Test
    public void testOneArg() {
    
        final String aQuery = sparqlPrefix +
                    "select ?result where { bind(geohash:bottom(\"gbsuv7ztgzpt\") AS ?result) }";

            try (final SelectQueryResult theResult = connection.select(aQuery).execute()) {

                assertThat(theResult).hasNext();
                final Value aValue = theResult.next().value("result").get();
                assertThat(aValue).isInstanceOf(Literal.class);
                final Literal aLiteral = (Literal)aValue;

                assertThat(aLiteral.label()).isEqualTo("gbsuv7ztgzps");
                assertThat(theResult).isExhausted();
            }
    }

    @Test
    public void testTooFewArgs() {

        final String aQuery = sparqlPrefix +
                    "select ?result where { bind(geohash:bottom() as ?result) }";

            try(final SelectQueryResult theResult = connection.select(aQuery).execute()) {

                assertThat(theResult).hasNext();

                final BindingSet aBindingSet = theResult.next();

                assertThat(aBindingSet).withFailMessage("Should have no bindings").isEmpty();
                assertThat(theResult).isExhausted();
            }
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = sparqlPrefix +
                    "select ?result where { bind(geohash:bottom(\"one\", \"two\") as ?result) }";

            try(final SelectQueryResult theResult = connection.select(aQuery).execute()) {
       
                assertThat(theResult).hasNext();

                final BindingSet aBindingSet = theResult.next();

                assertThat(aBindingSet).withFailMessage("Should have no bindings").isEmpty();
                assertThat(theResult).isExhausted();
            }
    }

    @Test
    public void testWrongTypeFirstArg() {

        final String aQuery = sparqlPrefix +
                    "select ?result where { bind(geohash:bottom(1) as ?result) }";

            try(final SelectQueryResult theResult = connection.select(aQuery).execute()) {
     
                assertThat(theResult).hasNext();

                final BindingSet aBindingSet = theResult.next();

                assertThat(aBindingSet).withFailMessage("Should have no bindings").isEmpty();
                assertThat(theResult).isExhausted();
            }
    }
}
