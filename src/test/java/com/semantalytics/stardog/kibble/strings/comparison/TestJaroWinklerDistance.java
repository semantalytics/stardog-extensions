package com.semantalytics.stardog.kibble.strings.comparison;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.*;

public class TestJaroWinklerDistance extends AbstractStardogTest {

    @Test
    public void testTwoArg() {

        final String aQuery = "prefix stringmetric: <" + StringMetricVocabulary.NAMESPACE + "> " +
                "select ?dist where { bind(stringmetric:jaroWinklerDistance(\"My string\", \"My tsring\") as ?dist) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final Value aValue = aResult.next().value("dist").get();

            assertThat(Literal.doubleValue((Literal)aValue)).isEqualTo(0.025925934);
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testThreeArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?distance where { bind(stringmetric:jaroWinklerDistance(\"My string\", \"My tsring\", 0.1) as ?distance) }";

        final SelectQueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final Value aValue = aResult.next().value("distance").get();

        assertThat(Literal.doubleValue((Literal)aValue)).isEqualTo(0.025925934);
        assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
    }

    @Test
    public void testThreeArgIncorrectTypeThirdArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?distance where { bind(stringmetric:jaroWinklerDistance(\"My string\", \"My tsring\", \"x\") as ?distance) }";

        final SelectQueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertThat(aBindingSet).isEmpty();
        assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?str where { bind(stringmetric:jaroWinklerDistance(\"one\", \"two\", 0.7, \"four\") as ?str) }";

        final SelectQueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertThat(aBindingSet).isEmpty();
        assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
    }

    @Test
    public void testTooFewArgs() {
        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?str where { bind(stringmetric:jaroWinklerDistance(7) as ?str) }";

        final SelectQueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertThat(aBindingSet).isEmpty();
        assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
    }
}
