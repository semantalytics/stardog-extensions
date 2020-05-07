package com.semantalytics.stardog.kibble.string.emoji;

import com.google.common.collect.Lists;
import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;


public class TestEmoticon extends AbstractStardogTest {

    @Test
    public void testOneArgEmoji() {

        final String aQuery = EmojiVocabulary.sparqlPrefix("emoji") +
                "select ?result where { unnest(emoji:emoticon(\"smile\") as ?result) }";

        List<String> results = new ArrayList<>();
        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            Literal aLiteral1 = (Literal)aResult.next().value("result").get();
            results.add(aLiteral1.label());

            final Literal aLiteral2 = (Literal)aResult.next().value("result").get();
            results.add(aLiteral2.label());

            final Literal aLiteral3 = (Literal)aResult.next().value("result").get();
            results.add(aLiteral3.label());

            final Literal aLiteral4 = (Literal)aResult.next().value("result").get();
            results.add(aLiteral4.label());

            assertThat(aResult).isExhausted();
        }
        assertThat(results).containsExactlyInAnyOrder("=-D", ":-D", ":D", "=D");
    }

    @Test
    public void testEmptyString() {

        final String aQuery = EmojiVocabulary.sparqlPrefix("emoji") +
                "select ?result where { unnest(emoji:emoticon(\"\") as ?result) }";

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
                "select ?result where { bind(emoji:emoticon() as ?result) }";

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
                "select ?result where { bind(emoji:emoticon(\"star\", \"dog\") as ?result) }";

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
                "select ?result where { bind(emoji:emoticon(1) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet.size()).isZero();
            assertThat(aResult.hasNext()).isFalse();
        }
    }

}
