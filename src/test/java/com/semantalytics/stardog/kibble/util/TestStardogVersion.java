package com.semantalytics.stardog.kibble.util;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import org.junit.*;
import org.openrdf.query.TupleQueryResult;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestStardogVersion  extends AbstractStardogTest {

 
    @Test
    public void testStardogVersion() throws Exception {

            final String aQuery = "prefix util: <" + UtilVocabulary.NAMESPACE + "> " +
                    "select ?result where { bind(util:stardogVersion() AS ?result) }";


            try (final TupleQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().getValue("result").stringValue();

                assertEquals("5.0.4", aValue);

                assertFalse("Should have no more results", aResult.hasNext());
            }


    }

}
