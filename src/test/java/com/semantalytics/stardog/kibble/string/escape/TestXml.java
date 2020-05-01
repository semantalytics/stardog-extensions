package com.semantalytics.stardog.kibble.string.escape;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import org.junit.Test;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

public class TestXml extends AbstractStardogTest {

    private static final String sparqlPrefix = EscapeVocabulary.sparqlPrefix("escape");

    @Test
    public void testOneArg() {

        final String aQuery = sparqlPrefix +
                "select ?result where { bind(escape:xml10(\"\\\"bread\\\" & \\\"butter\\\"\") AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final Value aValue = aResult.next().get("result");

            assertThat(aValue).isInstanceOf(Literal.class);

            final Literal aLiteralValue = (Literal)aValue;

            assertThat(aLiteralValue.label()).isEqualTo("&quot;bread&quot; &amp; &quot;butter&quot;");
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }
    
    @Test
    public void testEmptyString() {

        final String aQuery = sparqlPrefix +
                "select ?result where { bind(escape:xml10(\"\") as ?result) }";

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
                "select ?result where { bind(escape:xml10() as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = sparqlPrefix +
                "select ?result where { bind(escape:xml10(\"one\", \"two\") as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testWrongTypeFirstArg() {

        final String aQuery = sparqlPrefix +
                "select ?result where { bind(escape:xml10(1) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            assertTrue("Should have a result", aResult.hasNext());

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }
}
