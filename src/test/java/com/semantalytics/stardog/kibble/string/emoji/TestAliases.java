package com.semantalytics.stardog.kibble.string.emoji;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class TestAliases extends AbstractStardogTest {

    @Test
    public void testOneArgEmoji() {

            final String aQuery = EmojiVocabulary.sparqlPrefix("emoji") +
                    "select ?result where { unnest(emoji:aliases(\"\uD83D\uDC2D\") as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final Literal aLiteral = (Literal)aResult.next().value("result").get();

                assertThat(aLiteral.label()).isEqualTo("mouse");
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testEmptyString() {

        final String aQuery = EmojiVocabulary.sparqlPrefix("emoji") +
                "select ?result where { unnest(emoji:aliases(\"\") as ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            Optional<Value> aPossibleValue = aResult.next().value("result");

            assertThat(aPossibleValue).isNotPresent();
            assertThat(aResult).isExhausted();
        }
    }

    @Test
    public void testTooFewArgs() {

        final String aQuery = EmojiVocabulary.sparqlPrefix("emoji") +
                "select ?result where { bind(emoji:aliases() as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet.size()).isZero();
            assertThat(aResult.hasNext()).isFalse();
        }
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = EmojiVocabulary.sparqlPrefix("emoji") +
                "select ?result where { bind(emoji:aliases(\"star\", \"dog\") as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet.size()).isZero();
            assertThat(aResult.hasNext()).isFalse();
        }
    }

    @Test
    public void testWrongTypeFirstArg() {

        final String aQuery = EmojiVocabulary.sparqlPrefix("emoji") +
                "select ?result where { bind(emoji:aliases(1) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet.size()).isZero();
            assertThat(aResult.hasNext()).isFalse();
        }
    }
}
