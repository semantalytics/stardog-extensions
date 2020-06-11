package com.semantalytics.stardog.kibble.array;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class TestEquals extends AbstractStardogTest {

    @Test
    public void testEquals() {

        final String aQuery =ArrayVocabulary.sparqlPrefix("array") +
                "select (array:equals(?array1, ?array2) as ?result) where { bind(array:of(1, 2) as ?array1) bind(array:of(1, 2) as ?array2) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            assertThat(Literal.booleanValue(aResult.next().literal("result").get())).isTrue();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testNotEquals() {

        final String aQuery =ArrayVocabulary.sparqlPrefix("array") +
                "select (array:equals(?array1, ?array2) as ?result) where { bind(array:of(1, 2) as ?array1) bind(array:of(3, 4) as ?array2) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            assertThat(Literal.booleanValue(aResult.next().literal("result").get())).isFalse();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testWrongTypeFirstArg() {

        final String aQuery = ArrayVocabulary.sparqlPrefix("array") +
        "select (array:equals(?array1, ?array2) as ?result) where { bind(1 as ?array1) bind(array:of(3, 4) as ?array2) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testWrongTypeSecondArg() {
        final String aQuery = ArrayVocabulary.sparqlPrefix("array") +
        "select (array:equals(?array1, ?array2) as ?result) where { bind(array:of(1, 2) as ?array1) bind(1 as ?array2) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testTooManyArgs() {
        final String aQuery = ArrayVocabulary.sparqlPrefix("array") +
            "select (array:equals(?array1, ?array2, 3) as ?result) where { bind(array:of(1, 2) as ?array1) bind(array:of(3, 4) as ?array2) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }
}
