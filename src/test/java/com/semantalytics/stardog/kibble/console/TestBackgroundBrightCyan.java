package com.semantalytics.stardog.kibble.console;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class TestBackgroundBrightCyan extends AbstractStardogTest {

    private static final String consoleSparqlPrefix = ConsoleVocabulary.sparqlPrefix("console");

    @Test
    public void testNoArg() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:backgroundBrightCyan() AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Value aValue = aResult.next().get("result");
            assertThat(aValue).isInstanceOf(Literal.class);
            final Literal aLiteralValue = (Literal)aValue;

            assertThat(aLiteralValue.label()).isEqualTo("\u001b[106m");
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testOneArg() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:backgroundBrightCyan(\"Stardog\") AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Value aValue = aResult.next().get("result");
            assertThat(aValue).isInstanceOf(Literal.class);
            final Literal aLiteralValue = (Literal)aValue;

            assertThat(aLiteralValue.label()).isEqualTo("\u001b[106mStardog\u001b[109m");
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testTwoArg() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:backgroundBrightCyan(\"one\", \"two\") AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Value aValue = aResult.next().get("result");
            assertThat(aValue).isInstanceOf(Literal.class);
            final Literal aLiteralValue = (Literal)aValue;

            assertEquals("\u001b[106monetwo\u001b[109m", aValue);
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testEmptyString() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:backgroundBrightCyan(\"\") as ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Value aValue = aResult.next().get("result");
            assertThat(aValue).isInstanceOf(Literal.class);
            final Literal aLiteralValue = (Literal)aValue;

            assertEquals("\u001b[106m\u001b[109m", aValue);
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testOneArgInteger() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:backgroundBrightCyan(1) AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Value aValue = aResult.next().get("result");
            assertThat(aValue).isInstanceOf(Literal.class);
            final Literal aLiteralValue = (Literal)aValue;

            assertEquals("\u001b[106m1\u001b[109m", aValue);
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }
}
