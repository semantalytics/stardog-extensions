package com.semantalytics.stardog.kibble.strings.comparison;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.*;

public class TestSorensenDiceDistance extends AbstractStardogTest {

    @Test
    public void testSorensenDiceDistance() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?dist where { bind(stringmetric:sorensenDiceDistance(\"ABCDE\", \"ABCDFG\", 2) as ?dist) }";

        final SelectQueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final Value aValue = aResult.next().value("dist").get();

        assertThat(Literal.doubleValue((Literal)aValue)).isEqualTo(0.33333333333333337);
        assertFalse("Should have no more results", aResult.hasNext());
        assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
    }

    @Test
    public void testSorensenDiceDistanceTooManyArgs() {


        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?str where { bind(stringmetric:sorensenDiceDistance(\"one\", \"two\", \"three\", \"four\") as ?str) }";

            final SelectQueryResult aResult = connection.select(aQuery).execute();
         
            // there should be a result because implicit in the query is the singleton set, so because the bind
            // should fail due to the value error, we expect a single empty binding
            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

        assertThat(aBindingSet).isEmpty();
        assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
    }

    @Test
    public void testSorensenDiceDistanceWrongType() {


        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?str where { bind(stringmetric:sorensenDiceDistance(7) as ?str) }";

            final SelectQueryResult aResult = connection.select(aQuery).execute();
        
            // there should be a result because implicit in the query is the singleton set, so because the bind
            // should fail due to the value error, we expect a single empty binding
            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

        assertThat(aBindingSet).isEmpty();
        assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
    }
}
