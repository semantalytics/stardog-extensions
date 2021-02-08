package com.semantalytics.stardog.kibble.array;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.semantalytics.stardog.kibble.string.StringVocabulary;
import com.stardog.stark.Literal;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.assertTrue;

public class TestNGram extends AbstractStardogTest {

    @Test
    public void testTwoArg() {

        final String aQuery =ArrayVocabulary.sparqlPrefix("array") + " " + StringVocabulary.sparqlPrefix("string") +
            " select (array:toString(array:ngram(2, ?array)) as ?result) where { bind(string:charactersOf(\"Stardog\") as ?array) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Optional<Literal> aPossibleLiteral = aResult.next().literal("result");
            assertThat(aPossibleLiteral).isPresent();
            final Literal aLiteral = aPossibleLiteral.get();
            assertThat(aLiteral.label()).isEqualTo("[ [ \"S\"^^<http://www.w3.org/2001/XMLSchema#string> \"t\"^^<http://www.w3.org/2001/XMLSchema#string> ] [ \"t\"^^<http://www.w3.org/2001/XMLSchema#string> \"a\"^^<http://www.w3.org/2001/XMLSchema#string> ] [ \"a\"^^<http://www.w3.org/2001/XMLSchema#string> \"r\"^^<http://www.w3.org/2001/XMLSchema#string> ] [ \"r\"^^<http://www.w3.org/2001/XMLSchema#string> \"d\"^^<http://www.w3.org/2001/XMLSchema#string> ] [ \"d\"^^<http://www.w3.org/2001/XMLSchema#string> \"o\"^^<http://www.w3.org/2001/XMLSchema#string> ] [ \"o\"^^<http://www.w3.org/2001/XMLSchema#string> \"g\"^^<http://www.w3.org/2001/XMLSchema#string> ] ]");
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testTooFewArgs() {
        final String aQuery =ArrayVocabulary.sparqlPrefix("array") + " " + StringVocabulary.sparqlPrefix("string") +
                " select (array:toString(array:ngram(2)) as ?result) where { bind(string:charactersOf(\"Stardog\") as ?array) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            Assertions.assertThat(aBindingSet.size()).isZero();
            Assertions.assertThat(aResult.hasNext()).isFalse();

        }
    }

    @Test
    public void testTooManyArgs() {
        final String aQuery =ArrayVocabulary.sparqlPrefix("array") + " " + StringVocabulary.sparqlPrefix("string") +
                " select (array:toString(array:ngram(2, ?array, 3)) as ?result) where { bind(string:charactersOf(\"Stardog\") as ?array) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            Assertions.assertThat(aBindingSet.size()).isZero();
            Assertions.assertThat(aResult.hasNext()).isFalse();

        }
    }

    @Test
    public void firstArgHasWrongType() {
        final String aQuery =ArrayVocabulary.sparqlPrefix("array") + " " + StringVocabulary.sparqlPrefix("string") +
                " select (array:toString(array:ngram(\"first\", ?array)) as ?result) where { bind(string:charactersOf(\"Stardog\") as ?array) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            Assertions.assertThat(aBindingSet.size()).isZero();
            Assertions.assertThat(aResult.hasNext()).isFalse();

        }
    }

    @Test
    public void secondArgHasWrongType() {
        final String aQuery =ArrayVocabulary.sparqlPrefix("array") + " " + StringVocabulary.sparqlPrefix("string") +
                " select (array:toString(array:ngram(2, 3)) as ?result) where { bind(string:charactersOf(\"Stardog\") as ?array) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            Assertions.assertThat(aBindingSet.size()).isZero();
            Assertions.assertThat(aResult.hasNext()).isFalse();

        }
    }
}
