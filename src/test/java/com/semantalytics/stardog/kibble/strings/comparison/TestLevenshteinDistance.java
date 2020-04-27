package com.semantalytics.stardog.kibble.strings.comparison;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestLevenshteinDistance extends AbstractStardogTest {

    @Test
    public void testLevenshtein() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?dist where { bind(stringmetric:levenshteinDistance(\"My string\", \"My tring\") as ?dist) }";

        final SelectQueryResult aResult = connection.select(aQuery).execute();

        assertTrue("Should have a result", aResult.hasNext());

        final Value aValue = aResult.next().value("dist").get();

        assertThat(Literal.doubleValue((Literal)aValue)).isEqualTo(1.0);

        assertFalse("Should have no more results", aResult.hasNext());
        assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
    }

    @Test
    public void testLevenstheinTooManyArgs() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?str where { bind(stringmetric:levenshteinDistance(\"one\", \"two\", \"three\") as ?str) }";

        final SelectQueryResult aResult = connection.select(aQuery).execute();
        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertThat(aBindingSet).isEmpty();
        assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
    }

    @Test
    public void testLevenshteinWrongType() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?str where { bind(stringmetric:levenshteinDistance(7) as ?str) }";

        final SelectQueryResult aResult = connection.select(aQuery).execute();
        assertTrue("Should have a result", aResult.hasNext());

        final BindingSet aBindingSet = aResult.next();

        assertFalse("Should have no more results", aResult.hasNext());
        assertThat(aBindingSet).isEmpty();
        assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
    }
}
