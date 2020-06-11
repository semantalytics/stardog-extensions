package com.semantalytics.stardog.kibble.string;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Datatype;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import java.util.Optional;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class TestCapitalize extends AbstractStardogTest {

    @Test
    public void testNotCapitalized() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                "select ?result where { bind(string:capitalize(\"stardog\") AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Optional<Value> aPossibleValue = aResult.next().value("result");
            assertThat(aPossibleValue).isPresent();
            final Value aValue = aPossibleValue.get();
            assertThat(assertStringLiteral(aValue));
            final Literal aLiteral = (Literal) aValue;
            assertThat(aLiteral.label()).isEqualTo("Stardog");
            assertThat(aResult).isExhausted();
        }
    }

    @Test
    public void testCapitalized() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                "select ?result where { bind(string:capitalize(\"Stardog\") AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final Optional<Value> aPossibleValue = aResult.next().value("result");
            assertThat(aPossibleValue).isPresent();
            final Value aValue = aPossibleValue.get();
            assertThat(assertStringLiteral(aValue));
            final Literal aLiteral = (Literal) aValue;
            assertThat(aLiteral.label()).isEqualTo("Stardog");
            assertThat(aResult).isExhausted();
        }
    }

    @Test
    public void testAllCaps() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                "select ?result where { bind(string:capitalize(\"STARDOG\") AS ?result) }";


        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult.hasNext()).isTrue().withFailMessage("Query should have a result");
            final Optional<Value> aPossibleValue = aResult.next().value("result");
            assertThat(aPossibleValue).isPresent();
            final Value aValue = aPossibleValue.get();
            assertThat(assertStringLiteral(aValue)).isTrue();
            assertThat(((Literal) aValue).label()).isEqualTo("STARDOG");
            assertThat(aResult.hasNext()).isFalse();
        }
    }

    @Test
    public void testEmptyString() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                "select ?result where { bind(string:capitalize(\"\") as ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult.hasNext()).isTrue().withFailMessage("Query should have a result");
            final Optional<Value> aPossibleValue = aResult.next().value("result");
            assertThat(aPossibleValue).isPresent();
            final Value aValue = aPossibleValue.get();
            assertThat(assertStringLiteral(aValue)).isTrue();
            final Literal aLiteral = (Literal) aValue;
            assertThat(aLiteral.label()).isEqualTo("");
            assertThat(aResult).isExhausted();
        }
    }

    @Test
    public void testTooFewArgs() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                "select ?result where { bind(string:capitalize() as ?result) }";

        final SelectQueryResult aResult = connection.select(aQuery).execute();

        assertThat(aResult).hasNext().withFailMessage("Should have a result");

        final BindingSet aBindingSet = aResult.next();

        assertThat(aBindingSet).isEmpty();
        assertThat(aResult).isExhausted();
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                "select ?capitalize where { bind(string:capitalize(\"one\", \"two\") as ?result) }";

        final SelectQueryResult aResult = connection.select(aQuery).execute();

        assertThat(aResult).hasNext().withFailMessage("Should have a result");

        final BindingSet aBindingSet = aResult.next();

        assertThat(aBindingSet).isEmpty();
        assertThat(aResult).isExhausted();
    }

    @Test
    public void testArgumentWrongType() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                "select ?result where { bind(string:capitalize(1) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted();
        }
    }
}
