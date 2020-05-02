package com.semantalytics.stardog.kibble.console;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.assertj.core.api.Assertions.assertThat;

public class TestBackgroundBlack extends AbstractStardogTest {

    private static final String consoleSparqlPrefix = ConsoleVocabulary.sparqlPrefix("console");

    @Test
    public void testNoArg() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:backgroundBlack() AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Value aValue = aResult.next().get("result");
            assertThat(aValue).isInstanceOf(Literal.class);
            final Literal aLiteralValue = (Literal)aValue;

            assertThat(aLiteralValue.label()).isEqualTo("\u001b[40m");
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testOneArg() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:backgroundBlack(\"Stardog\") AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Value aValue = aResult.next().get("result");
            assertThat(aValue).isInstanceOf(Literal.class);
            final Literal aLiteralValue = (Literal)aValue;

            assertThat(aLiteralValue.label()).isEqualTo("\u001b[40mStardog\u001b[49m");
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testTwoArg() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:backgroundBlack(\"one\", \"two\") AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Value aValue = aResult.next().get("result");
            assertThat(aValue).isInstanceOf(Literal.class);
            final Literal aLiteralValue = (Literal)aValue;

            assertThat(aLiteralValue.label()).isEqualTo("\u001b[40monetwo\u001b[49m");
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testEmptyString() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:backgroundBlack(\"\") as ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Value aValue = aResult.next().get("result");
            assertThat(aValue).isInstanceOf(Literal.class);
            final Literal aLiteralValue = (Literal)aValue;

            assertThat(aLiteralValue.label()).isEqualTo("\u001b[40m\u001b[49m");
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testOneArgInteger() {

        final String aQuery = consoleSparqlPrefix +
                "select ?result where { bind(console:backgroundBlack(1) AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Value aValue = aResult.next().get("result");
            assertThat(aValue).isInstanceOf(Literal.class);
            final Literal aLiteralValue = (Literal)aValue;

            assertThat(aLiteralValue.label()).isEqualTo("\u001b[40m1\u001b[49m");
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }
}
