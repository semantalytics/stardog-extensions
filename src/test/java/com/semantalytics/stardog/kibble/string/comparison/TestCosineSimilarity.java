package com.semantalytics.stardog.kibble.string.comparison;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import java.util.Optional;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertLiteral;
import static org.assertj.core.api.Assertions.*;

public class TestCosineSimilarity extends AbstractStardogTest {

    @Test
    public void testCosineTwoArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:cosineSimilarity(\"ABC\", \"ABCE\") as ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final Optional<Value> aPossibleValue = aResult.next().value("result");
            final Value aValue = aPossibleValue.get();

            assertThat(assertLiteral(aValue));
            assertThat(Literal.doubleValue((Literal)aValue)).isEqualTo(0.7071067811865475);
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testCosineThreeArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:cosineSimilarity(\"ABC\", \"ABCE\", 3) as ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            Optional<Value> aPossibleValue = aResult.next().value("result");
            assertThat(aPossibleValue).isPresent();

            final Value aValue = aPossibleValue.get();

            assertThat(Literal.doubleValue((Literal)aValue)).isEqualTo(0.7071067811865475);
            assertThat(aResult).withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testCosineTooManyArgs() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:cosineSimilarity(\"one\", \"two\", \"three\", \"four\") as ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testCosineWrongTypeFirstArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:cosineSimilarity(7) as ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testCosineWrongTypeSecondArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:cosineSimilarity(\"Stardog\", 2) as ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testCosineWrongTypeThirdArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:cosineSimilarity(\"Stardog\", \"Starlight\", \"Starship\") as ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testCosineThirdArgNotConstant() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                "select ?result where { bind(stringmetric:cosineSimilarity(\"Stardog\", \"Starlight\", ?thirdArg) as ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }
}
