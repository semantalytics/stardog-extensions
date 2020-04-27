package com.semantalytics.stardog.kibble.javascript;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import org.junit.*;
import org.openrdf.query.TupleQueryResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class TestExecute extends AbstractStardogTest {

    private final String sparqlPrefix = JavascriptVocabulary.sparqlPrefix("js");

    @Test
    public void testExecuteString() {

            final String aQuery = sparqlPrefix +
                    " select ?result where { bind(js:exec(\"values[0].stringValue() + \' \' + values[1].stringValue()\", \"Hello\", \"world!\") as ?result) }";

            try (final TupleQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final String aValue = aResult.next().getValue("result").stringValue();

                assertEquals("Hello world!", aValue);
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testExecuteDouble() {

        connection.begin();

        final String aQuery = sparqlPrefix +
                " select ?result where { bind(js:exec(\"parseInt(values[0].stringValue()) + parseInt(values[1].stringValue())\", 21, 35) as ?result) }";

        try (final TupleQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final String aValue = aResult.next().getValue("result").stringValue();

            assertEquals(56.0, Double.parseDouble(aValue), 0.000001);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

}
