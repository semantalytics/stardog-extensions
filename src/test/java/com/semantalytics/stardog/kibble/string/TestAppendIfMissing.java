package com.semantalytics.stardog.kibble.string;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class TestAppendIfMissing extends AbstractStardogTest {

    @Test
    public void testMissing() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                "select ?result where { bind(string:appendIfMissing(\"Stardog\", \".txt\") AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult.hasNext()).isTrue();

            final String aValue = aResult.next().value("result").get().toString();

            assertThat(aValue).isEqualTo("\"Stardog.txt\"^^<http://www.w3.org/2001/XMLSchema#string>");
            assertThat(aResult.hasNext()).isFalse();
        }
    }

    @Test
    public void testNotMissing() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                "select ?result where { bind(string:appendIfMissing(\"Stardog.txt\", \".txt\") as ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult.hasNext()).isTrue();

            final Optional<Value> aPossibleValue = aResult.next().value("result");
            assertThat(aPossibleValue).isPresent();
            final String aValue = aPossibleValue.get().toString();

            assertThat(aValue).isEqualTo("\"Stardog.txt\"^^<http://www.w3.org/2001/XMLSchema#string>");
            assertThat(aResult.hasNext()).isFalse();
        }
    }

    @Test
    public void testTooFewArgs() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                "select ?result where { bind(string:appendIfMissing(\"one\") as ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult.hasNext()).isTrue();

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet.variables()).isEmpty();
            assertThat(aResult.hasNext()).isFalse();
        }
    }

    @Test
    public void testWrongTypeFirstArg() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                "select ?result where { bind(string:appendIfMissing(4, 5) as ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult.hasNext()).isTrue();

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet.variables()).isEmpty();
            assertThat(aResult.hasNext()).isFalse();
        }
    }

    @Test
    public void testWrongTypeSecondArg() {


        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                "select ?result where { bind(string:appendIfMissing(\"one\", 2) as ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult.hasNext()).isTrue();

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet.variables()).isEmpty();
            assertThat(aResult.hasNext()).isFalse();
        }
    }

    @Test
    public void testWrongTypeThirdArg() {


        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                "select ?result where { bind(string:appendIfMissing(\"one\", \"two\", 3) as ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult.hasNext()).isTrue();

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet.variables()).isEmpty();
            assertThat(aResult.hasNext()).isFalse();
        }
    }

    @Test
    public void testLengthTooShort() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                "select ?result where { bind(string:appendIfMissing(\"Stardog\", 3) as ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult.hasNext()).isTrue();

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet.variables()).isEmpty();
            assertThat(aResult.hasNext()).isFalse();
        }
    }
}
