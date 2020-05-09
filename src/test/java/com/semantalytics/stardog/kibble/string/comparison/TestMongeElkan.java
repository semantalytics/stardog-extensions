package com.semantalytics.stardog.kibble.string.comparison;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.*;

public class TestMongeElkan extends AbstractStardogTest {

    @Test
    public void testCosineTwoArg() {


        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?result where { bind(stringmetric:cosineDistance(\"ABC\", \"ABCE\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final Value aValue = aResult.next().value("result").get();

                assertThat(Literal.doubleValue((Literal)aValue)).isEqualTo(0.29289321881345254);
                assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
            }
    }

    @Test
    public void testCosineThreeArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?result where { bind(stringmetric:cosineDistance(\"ABC\", \"ABCE\", 3) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final Value aValue = aResult.next().value("result").get();

                assertThat(Literal.doubleValue((Literal)aValue)).isEqualTo(0.29289321881345254);
                assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
            }
    }

    @Test
    public void testCosineTooManyArgs() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?result where { bind(stringmetric:cosineDistance(\"one\", \"two\", \"three\", \"four\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
            }
    }

    @Test
    public void testCosineWrongTypeFirstArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?result where { bind(stringmetric:cosineDistance(7) as ?result) }";

            final SelectQueryResult aResult = connection.select(aQuery).execute();
            try {
                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
            }
            finally {
                aResult.close();
            }
    }

    @Test
    public void testCosineWrongTypeSecondArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?result where { bind(stringmetric:cosineDistance(\"Stardog\", 2) as ?result) }";

            final SelectQueryResult aResult = connection.select(aQuery).execute();
            try {
                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
            }
            finally {
                aResult.close();
            }
    }

    @Test
    public void testCosineWrongTypeThirdArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?result where { bind(stringmetric:cosineDistance(\"Stardog\", \"Starlight\", \"Starship\") as ?result) }";

            final SelectQueryResult aResult = connection.select(aQuery).execute();
            try {
                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
            }
            finally {
                aResult.close();
            }
    }

    @Test
    public void testCosineThirdArgNotConstant() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?result where { bind(stringmetric:cosineDistance(\"Stardog\", \"Starlight\", ?thirdArg) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
            }
    }
}
