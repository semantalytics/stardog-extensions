package com.semantalytics.stardog.kibble.net.internetaddress;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import org.junit.Test;
import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQueryResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestIsValidIp6 extends AbstractStardogTest {

    @Test
    public void testOneArg() {

            final String aQuery = InternetAddressVocabulary.sparqlPrefix("ip") +
                    " select ?result where { bind(ip:toNumber(\"192.168.0.1\") as ?result) }";

            try (final TupleQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final long aValue = Long.parseLong(aResult.next().getValue("result").stringValue());

                assertEquals(3232235521L, aValue);
                assertFalse("Should have no more results", aResult.hasNext());
            } 
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = InternetAddressVocabulary.sparqlPrefix("ip") +
                "select ?result where { bind(ip:toNumber(\"one\", \"two\") as ?result) }";

        try(final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testTooFewArgs() {

        final String aQuery = InternetAddressVocabulary.sparqlPrefix("ip") +
                "select ?result where { bind(ip:toNumber() as ?result) }";

        try(final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testWrongTypeFistArg() {

        final String aQuery = InternetAddressVocabulary.sparqlPrefix("ip") +
                "select ?result where { bind(ip:toNumber(1) as ?result) }";

        try(final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }
}
