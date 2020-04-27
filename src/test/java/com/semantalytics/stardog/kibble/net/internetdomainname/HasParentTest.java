package com.semantalytics.stardog.kibble.net.internetdomainname;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.semantalytics.stardog.kibble.net.inetaddress.InetAddressVocabulary;
import org.junit.Test;
import org.openrdf.query.BindingSet;
import org.openrdf.query.TupleQueryResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HasParentTest extends AbstractStardogTest {

    @Test
    public void testInetAddressToNumber() {

            final String aQuery = "prefix dn: <" + InternetDomainNameVocabulary.NAMESPACE + "> " +
                    "select ?result where { bind(dn:hasParent(\"stardog.com\") as ?result) }";

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
                "select ?result where { bind(dn:hasParent(\"one\", \"two\") as ?result) }";

        final TupleQueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

        assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testTooFewArgs() {

        final String aQuery = InternetDomainNameVocabulary.sparqlPrefix("dn") +
                "select ?result where { bind(dn:hasParent() as ?result) }";

        final TupleQueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

        assertFalse("Should have no more results", aResult.hasNext());
    }

    @Test
    public void testFirstArgWrongType() {

        final String aQuery = InternetDomainNameVocabulary.sparqlPrefix("dn") +
                "select ?result where { bind(dn:hasParent(1) as ?result) }";

        final TupleQueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertTrue("Should have no bindings", aBindingSet.getBindingNames().isEmpty());

        assertFalse("Should have no more results", aResult.hasNext());
    }
}
