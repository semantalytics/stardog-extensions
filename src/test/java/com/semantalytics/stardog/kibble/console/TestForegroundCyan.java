package com.semantalytics.stardog.kibble.console;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class TestForegroundCyan extends AbstractStardogTest {

    private static final String consoleSparqlPrefix = ConsoleVocabulary.sparqlPrefix("console");

    @Test
    public void testNoArg() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:foregroundCyan() AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Value aValue = aResult.next().get("result");
            assertThat(aValue).isInstanceOf(Literal.class);
            final Literal aLiteralValue = (Literal)aValue;

            assertThat(aLiteralValue.label()).isEqualTo("\u001b[46m");
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testOneArg() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:foregroundCyan(\"Stardog\") AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Value aValue = aResult.next().get("result");
            assertThat(aValue).isInstanceOf(Literal.class);
            final Literal aLiteralValue = (Literal)aValue;

            assertThat(aLiteralValue.label()).isEqualTo("\u001b[46mStardog\u001b[49m");
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testTwoArg() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:foregroundCyan(\"one\", \"two\") AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Value aValue = aResult.next().get("result");
            assertThat(aValue).isInstanceOf(Literal.class);
            final Literal aLiteralValue = (Literal)aValue;

            assertThat(aLiteralValue.label()).isEqualTo("\u001b[46monetwo\u001b[49m");
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testEmptyString() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:foregroundCyan(\"\") as ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Value aValue = aResult.next().get("result");
            assertThat(aValue).isInstanceOf(Literal.class);
            final Literal aLiteralValue = (Literal)aValue;

            assertThat(aLiteralValue.label()).isEqualTo("\u001b[46m\u001b[49m");
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testOneArgInteger() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:foregroundCyan(1) AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Value aValue = aResult.next().get("result");
            assertThat(aValue).isInstanceOf(Literal.class);
            final Literal aLiteralValue = (Literal)aValue;

            assertThat(aLiteralValue.label()).isEqualTo("\u001b[46m1\u001b[49m");
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }
}
