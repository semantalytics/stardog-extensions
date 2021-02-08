package com.semantalytics.stardog.kibble.net.internetaddress;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class TestIsValidIp4 extends AbstractStardogTest {

    @Test
    public void testOneArgTrue() {

            final String aQuery = InternetAddressVocabulary.sparqlPrefix("ip") +
                    " select ?result where { bind(ip:isValidIp4(\"192.168.0.1\") as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final Value aValue = aResult.next().value("result").get();

                assertThat(Literal.booleanValue((Literal)aValue)).isTrue();
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testOneArgFalse() {

        final String aQuery = InternetAddressVocabulary.sparqlPrefix("ip") +
                " select ?result where { bind(ip:isValidIp4(\"2001:0db8:85a3:0000:0000:8a2e:0370:7334\") as ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final Value aValue = aResult.next().value("result").get();

            assertThat(Literal.booleanValue((Literal)aValue)).isFalse();
            assertThat(aResult).isExhausted();
        }
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = InternetAddressVocabulary.sparqlPrefix("ip") +
                "select ?result where { bind(ip:isValidIp4(\"one\", \"two\") as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult.hasNext()).isFalse();
        }
    }

    @Test
    public void testTooFewArgs() {

        final String aQuery = InternetAddressVocabulary.sparqlPrefix("ip") +
                "select ?result where { bind(ip:isValidIp4() as ?result) }";

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
                "select ?result where { bind(ip:isValidIp4(1) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult.hasNext()).isFalse();
        }
    }
}
