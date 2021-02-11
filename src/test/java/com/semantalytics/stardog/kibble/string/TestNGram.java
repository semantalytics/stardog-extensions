package com.semantalytics.stardog.kibble.string;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.semantalytics.stardog.kibble.array.ArrayVocabulary;
import com.stardog.stark.Literal;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class TestNGram extends AbstractStardogTest {

    @Test
    public void testTwoArg() {

        final String aQuery = ArrayVocabulary.sparqlPrefix("array") + " " + StringVocabulary.sparqlPrefix("string") +
            " select (string:joinWith(\",\", string:ngram(2, ?string)) as ?result) where { bind(\"Stardog\" as ?string) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Optional<Literal> aPossibleLiteral = aResult.next().literal("result");
            assertThat(aPossibleLiteral).isPresent();
            final Literal aLiteral = aPossibleLiteral.get();
            System.out.println(aLiteral);
            assertThat((aLiteral.label())).isEqualTo("St,ta,ar,rd,do,og");
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testFourArg() {

        final String aQuery = ArrayVocabulary.sparqlPrefix("array") + " " + StringVocabulary.sparqlPrefix("string") +
                " select (string:joinWith(\",\", string:ngram(2, ?string, \"_\", \"_\")) as ?result) where { bind(\"Stardog\" as ?string) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Optional<Literal> aPossibleLiteral = aResult.next().literal("result");
            assertThat(aPossibleLiteral).isPresent();
            final Literal aLiteral = aPossibleLiteral.get();
            System.out.println(aLiteral);
            assertThat((aLiteral.label())).isEqualTo("_S,St,ta,ar,rd,do,og,g_");
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }
}
