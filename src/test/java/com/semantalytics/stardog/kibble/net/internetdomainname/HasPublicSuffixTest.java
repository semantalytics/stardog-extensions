package com.semantalytics.stardog.kibble.net.internetdomainname;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.semantalytics.stardog.kibble.net.inetaddress.InetAddressVocabulary;
import org.junit.Test;
import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQueryResult;

import static org.junit.Assert.*;

public class HasPublicSuffixTest extends AbstractStardogTest {

    @Test
    public void testHasPublicSuffix() {

        final String aQuery = InternetDomainNameVocabulary.sparqlPrefix("dn") +
                    "select ?result where { bind(dn:hasPublicSuffix(\"stardog.com\") as ?result) }";

            try (final TupleQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final boolean aValue = Boolean.parseBoolean(aResult.next().getValue("result").stringValue());

                assertEquals(true, aValue);

                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = InternetDomainNameVocabulary.sparqlPrefix("dn") +
                "select ?result where { bind(dn:hasPublicSuffix(\"one\", \"two\") as ?result) }";

        final TupleQueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

        assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testTooFewArgs() {

        final String aQuery = InternetDomainNameVocabulary.sparqlPrefix("dn") +
                "select ?result where { bind(dn:hasPublicSuffix() as ?result) }";

        final TupleQueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

        assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testFirstArgWrongType() {

        final String aQuery = InternetDomainNameVocabulary.sparqlPrefix("dn") +
                "select ?result where { bind(dn:hasPublicSuffix(1) as ?result) }";

        final TupleQueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

        assertFalse("Should have no more results", aResult.hasNext());
    }
}
