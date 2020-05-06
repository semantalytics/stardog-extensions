package com.semantalytics.stardog.kibble.string.emoji;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class TestCount extends AbstractStardogTest {

    @Test
    public void testNoEmojis() {

        final String aQuery = EmojiVocabulary.sparqlPrefix("emoji") +
                    "select ?result where { bind(emoji:count(\"\") as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final Literal aLiteral = (Literal)aResult.next().value("result").get();

                assertEquals("0", aLiteral.label());
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testOneEmoji() {

        final String aQuery = EmojiVocabulary.sparqlPrefix("emoji") +
                "select ?result where { bind(emoji:count(\":star:\") as ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final Literal aLiteral = ((Literal)aResult.next().value("result").get());

            assertEquals("1", aLiteral.label());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testTwoEmoji() {

        final String aQuery = EmojiVocabulary.sparqlPrefix("emoji") +
                "select ?result where { bind(emoji:count(\":star: :dog:\") as ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final Literal aLiteral = (Literal)aResult.next().value("result").get();

            assertEquals("2", aLiteral.label());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = EmojiVocabulary.sparqlPrefix("emoji") +
                "select ?result where { bind(emoji:count(\"star\", \"dog\") as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet.size()).isZero();
            assertThat(aResult.hasNext()).isFalse();
        }
    }

    @Test
    public void testTooFewArgs() {

        final String aQuery = EmojiVocabulary.sparqlPrefix("emoji") +
                "select ?result where { bind(emoji:count() as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet.size()).isZero();
            assertThat(aResult.hasNext()).isFalse();
        }
    }

    @Test
    public void testWrongTypeFistArg() {

        final String aQuery = EmojiVocabulary.sparqlPrefix("emoji") +
                "select ?result where { bind(emoji:count(1) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet.size()).isZero();
            assertThat(aResult.hasNext()).isFalse();
        }
    }

}
