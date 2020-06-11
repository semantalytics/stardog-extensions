package com.semantalytics.stardog.kibble.string.emoji;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class TestIsEmoji extends AbstractStardogTest {

    @Test
    public void testIsNotEmoji() {

        final String aQuery = EmojiVocabulary.sparqlPrefix("emoji") +
                    "select ?result where { bind(emoji:isEmoji(\"stardog\") as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final boolean aValue = Literal.booleanValue((Literal)aResult.next().value("result").get());

                assertEquals(false, aValue);
                assertFalse("Should have no more results", aResult.hasNext());
            }
    }

    @Test
    public void testIsEmoji() {


        final String aQuery = EmojiVocabulary.sparqlPrefix("emoji") +
                "select ?result where { bind(emoji:isEmoji(\"dog\") as ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final boolean aValue = Literal.booleanValue((Literal)aResult.next().value("result").get());

            assertEquals(true, aValue);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testIsEmojiTooManyArgs() {

        final String aQuery = EmojiVocabulary.sparqlPrefix("emoji") +
                "select ?result where { bind(emoji:isEmoji(\"star\", \"dog\") as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet.size()).isZero();
            assertThat(aResult.hasNext()).isFalse();
        }
    }

}
