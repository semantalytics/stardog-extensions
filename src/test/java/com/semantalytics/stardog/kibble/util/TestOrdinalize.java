package com.semantalytics.stardog.kibble.util;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.junit.Assert.*;


public class TestOrdinalize extends AbstractStardogTest {

    final static String sparqlPrefix = UtilVocabulary.sparqlPrefix("util");

    @Test
    public void testOrdinalize() {

            final String aQuery = sparqlPrefix
                    + "select ?result where { bind(util:ordinalize(1) as ?result) } ";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().literal("result").get().label();

                assertEquals("1st", aValue);
                assertFalse("Should have no more results", aResult.hasNext());

            }
    }
}
