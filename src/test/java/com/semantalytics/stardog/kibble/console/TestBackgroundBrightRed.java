package com.semantalytics.stardog.kibble.console;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class TestBackgroundBrightRed extends AbstractStardogTest {

    private static final String consoleSparqlPrefix = ConsoleVocabulary.sparqlPrefix("console");

    @Test
    public void testNoArg() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:backgroundBrightRed() AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Value aValue = aResult.next().get("result");
            assertThat(aValue).isInstanceOf(Literal.class);
            final Literal aLiteralValue = (Literal)aValue;

            assertEquals("\u001b[101m", aValue);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testOneArg() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:backgroundBrightRed(\"Stardog\") AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Value aValue = aResult.next().get("result");
            assertThat(aValue).isInstanceOf(Literal.class);
            final Literal aLiteralValue = (Literal)aValue;

            assertEquals("\u001b[101mStardog\u001b[109m", aValue);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testTwoArg() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:backgroundBrightRed(\"one\", \"two\") AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Value aValue = aResult.next().get("result");
            assertThat(aValue).isInstanceOf(Literal.class);
            final Literal aLiteralValue = (Literal)aValue;

            assertEquals("\u001b[101monetwo\u001b[109m", aValue);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testEmptyString() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:backgroundBrightRed(\"\") as ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Value aValue = aResult.next().get("result");
            assertThat(aValue).isInstanceOf(Literal.class);
            final Literal aLiteralValue = (Literal)aValue;

            assertEquals("\u001b[101m\u001b[109m", aValue);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testOneArgInteger() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:backgroundBrightRed(1) AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Value aValue = aResult.next().get("result");
            assertThat(aValue).isInstanceOf(Literal.class);
            final Literal aLiteralValue = (Literal)aValue;

            assertEquals("\u001b[101m1\u001b[109m", aValue);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }
}
