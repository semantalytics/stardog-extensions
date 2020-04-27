package com.semantalytics.stardog.kibble.date;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import org.junit.*;
import org.openrdf.query.TupleQueryResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class EpochTimeTest extends AbstractStardogTest {

    @Test
    public void testEpochTime() throws Exception {

            connection.begin();

            final String aQuery = "prefix date: <" + DateVocabulary.NAMESPACE + ">" +
                    "select ?result where { bind(date:epochTime(\"2017-09-01\"^^xsd:date) as ?result) }";

            try (final TupleQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final long aValue = Long.parseLong(aResult.next().getValue("result").stringValue());

                assertEquals(1504238400000L, aValue);

                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

}
