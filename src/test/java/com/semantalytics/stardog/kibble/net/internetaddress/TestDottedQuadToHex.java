package com.semantalytics.stardog.kibble.net.internetaddress;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class TestDottedQuadToHex extends AbstractStardogTest {

    private static final String sparqlPrefix = InternetAddressVocabulary.sparqlPrefix("ip");

    @Test
    public void testOneArg() throws Exception {

            final String aQuery = sparqlPrefix +
                    " select ?result where { bind(ip:dottedQuadToHex(3232235521) as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final Value aValue = aResult.next().value("result").get();

                assertThat(((Literal)aValue).label()).isEqualTo("192.168.0.1");
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = InternetAddressVocabulary.sparqlPrefix("ip") +
                "select ?result where { bind(ip:dottedQuadToHex(\"one\", \"two\") as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted();
        }
    }

    @Test
    public void testTooFewArgs() {

        final String aQuery = InternetAddressVocabulary.sparqlPrefix("ip") +
                "select ?result where { bind(ip:dottedQuadToHex() as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult.hasNext()).isFalse();
        }
    }

    @Test
    public void testWrongTypeFistArg() {

        final String aQuery = InternetAddressVocabulary.sparqlPrefix("ip") +
                "select ?result where { bind(ip:dottedQuadToHex(\"one\") as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult.hasNext()).isFalse();
        }
    }
}
