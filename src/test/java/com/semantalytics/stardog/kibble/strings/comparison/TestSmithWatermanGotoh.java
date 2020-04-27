package com.semantalytics.stardog.kibble.strings.comparison;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.*;

public class TestSmithWatermanGotoh extends AbstractStardogTest {

    @Test
    public void testSmithWatermanGotohTwoArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:smithWatermanGotoh(\"Stardog\", \"Starman\") as ?result) }";

        final SelectQueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final Value aValue = aResult.next().value("result").get();

        assertThat(Literal.doubleValue((Literal)aValue)).isEqualTo(0.5714286);
        assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
    }

    @Test
    public void testSmithWatermanGotohFiveArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:smithWatermanGotoh(\"Stardog\", \"Starman\", -0.5, 1.0, -2.0) as ?result) }";

        final SelectQueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final Value aValue = aResult.next().value("result").get();

        assertThat(Literal.doubleValue((Literal)aValue)).isEqualTo(0.5714286);
        assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
    }

    @Test
    public void testSmithWatermanGotohTooManyArgs() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:smithWatermanGotoh(\"one\", \"two\", \"three\", \"four\", \"five\", \"six\") as ?result) }";

        final SelectQueryResult aResult = connection.select(aQuery).execute();
        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertThat(aBindingSet).isEmpty();
        assertThat(aResult).isExhausted().withFailMessage("Should have no more results");

    }

    @Test
    public void testSmithWatermanGotohWrongType() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:smithWatermanGotoh(7) as ?result) }";

        final SelectQueryResult aResult = connection.select(aQuery).execute();
        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertThat(aBindingSet).isEmpty();
        assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
    }
}
