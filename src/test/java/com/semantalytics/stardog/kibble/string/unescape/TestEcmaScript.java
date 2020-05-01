package com.semantalytics.stardog.kibble.string.unescape;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.junit.Test;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

public class TestEcmaScript extends AbstractStardogTest {

    private static final String sparqlPrefix = UnescapeVocabulary.sparqlPrefix("unescape");

    @Test
    public void testOneArg() {

        final String aQuery = sparqlPrefix +
                "select ?result where { bind(unescape:ecmaScript(\"He didn't say, \\\"Stop!\\\"\") AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final Value aValue = aResult.next().get("result");

            assertThat(aValue).isInstanceOf(Literal.class);

            final Literal aLiteralValue = (Literal)aValue;

            assertThat(aLiteralValue.label()).isEqualTo("He didn\'t say, \"Stop!\"");
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testEmptyString() {

        final String aQuery = sparqlPrefix +
                "select ?result where { bind(unescape:ecmaScript(\"\") as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final Value aValue = aResult.next().get("result");

            assertThat(aValue).isInstanceOf(Literal.class);

            final Literal aLiteralValue = (Literal)aValue;

            assertThat(aLiteralValue.label()).isEqualTo("");
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testTooFewArgs() {

        final String aQuery = sparqlPrefix +
                "select ?result where { bind(unescape:ecmaScript() as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = sparqlPrefix +
                "select ?result where { bind(unescape:ecmaScript(\"one\", \"two\") as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testWrongTypeFirstArg() {

        final String aQuery = sparqlPrefix +
                "select ?result where { bind(unescape:ecmaScript(1) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

}
