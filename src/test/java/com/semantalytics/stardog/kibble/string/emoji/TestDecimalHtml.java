package com.semantalytics.stardog.kibble.string.emoji;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class TestDecimalHtml extends AbstractStardogTest {

    @Test
    public void testOneArg() {

        final String aQuery = EmojiVocabulary.sparqlPrefix("emoji") +
                    "select ?result where { bind(emoji:decimalHtml(\"dog\") as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final Literal aLiteral = (Literal)aResult.next().value("result").get();

                assertEquals("&#128054;", aLiteral.label());
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testNotEmojiString() {

        final String aQuery = EmojiVocabulary.sparqlPrefix("emoji") +
                "select ?result where { bind(emoji:decimalHtml(\"notemoji\") as ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final Literal aLiteral = (Literal)aResult.next().value("result").get();

            assertEquals("", aLiteral.label());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testEmptyString() {

        final String aQuery = EmojiVocabulary.sparqlPrefix("emoji") +
                "select ?result where { bind(emoji:decimalHtml(\"\") as ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final Literal aLiteral = (Literal)aResult.next().value("result").get();

            assertEquals("", aLiteral.label());
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = EmojiVocabulary.sparqlPrefix("emoji") +
                "select ?result where { bind(emoji:decimalHtml(\"one\", \"two\") as ?result) }";

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
                "select ?result where { bind(emoji:decimalHtml() as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet.size()).isZero();
            assertThat(aResult.hasNext()).isFalse();
        }
    }

    @Test
    public void wrongTypeFirstArg() {

        final String aQuery = EmojiVocabulary.sparqlPrefix("emoji") +
                "select ?result where { bind(emoji:decimalHtml(1) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet.size()).isZero();
            assertThat(aResult.hasNext()).isFalse();
        }
    }
}
