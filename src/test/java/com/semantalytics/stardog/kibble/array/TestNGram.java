package com.semantalytics.stardog.kibble.array;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.semantalytics.stardog.kibble.string.StringVocabulary;
import com.stardog.stark.Literal;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

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
            System.out.println(aLiteral);
            assertThat(Literal.booleanValue(aLiteral)).isTrue();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }
}
