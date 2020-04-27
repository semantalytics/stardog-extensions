package com.semantalytics.stardog.kibble.net.internetaddress;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import org.junit.Test;
import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQueryResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestInternetNumberToAddress extends AbstractStardogTest {

    @Test
    public void testOneArg() throws Exception {

            final String aQuery = InternetAddressVocabulary.sparqlPrefix("ip") +
                    " select ?result where { bind(ip:toAddress(3232235521) as ?result) }";

            try (final TupleQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().getValue("result").stringValue();

                assertEquals("192.168.0.1", aValue);
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = InternetAddressVocabulary.sparqlPrefix("ip") +
                "select ?result where { bind(ip:toAddress(\"one\", \"two\") as ?result) }";

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
                "select ?result where { bind(ip:toAddress() as ?result) }";

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
                "select ?result where { bind(ip:toAddress(\"one\") as ?result) }";

        try(final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }
}
