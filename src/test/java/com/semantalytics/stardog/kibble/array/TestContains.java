package com.semantalytics.stardog.kibble.array;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class TestContains extends AbstractStardogTest {

    @Test
    public void testOneArgTrue() {

        final String aQuery =ArrayVocabulary.sparqlPrefix("array") +
            "select (array:contains(?array, 2) as ?result) where { bind(array:of(1, 2, 3, 4) as ?array) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Optional<Literal> aPossibleLiteral = aResult.next().literal("result");
            assertThat(aPossibleLiteral).isPresent();
            final Literal aLiteral = aPossibleLiteral.get();
            assertThat(Literal.booleanValue(aLiteral)).isTrue();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testOneArgFalse() {

        final String aQuery =ArrayVocabulary.sparqlPrefix("array") +
            "select (array:contains(?array, 5) as ?result) where { bind(array:of(1, 2, 3, 4) as ?array) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Optional<Literal> aPossibleLiteral = aResult.next().literal("result");
            assertThat(aPossibleLiteral).isPresent();
            final Literal aLiteral = aPossibleLiteral.get();
            assertThat(Literal.booleanValue(aLiteral)).isFalse();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testWrongTypeFirstArg() {

        final String aQuery = ArrayVocabulary.sparqlPrefix("array") +
            "select (array:contains(1, 5) as ?result) where { values ?value { 1 } } ";

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
            "select (array:contains(set(?value), 5, 6) as ?result) where { values ?value { 1 2 3 4} } ";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }
}
