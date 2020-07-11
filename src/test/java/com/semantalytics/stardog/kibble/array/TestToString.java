package com.semantalytics.stardog.kibble.array;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Optional;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class TestToString extends AbstractStardogTest {

    final String sparqlPrefix = ArrayVocabulary.sparqlPrefix("array");

    @Test
    public void happyPathSimpleArray() {

        final String aQuery = sparqlPrefix +
                "select ?result where { { select (array:toString(set(?value)) as ?array) where { values ?value { 1 2 } } } unnest(?array as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            Assertions.assertThat(aResult).hasNext();
            final Optional<Value> aPossibleValue = aResult.next().value("result");
            Assertions.assertThat(aPossibleValue).isPresent();
            final Value aValue = aPossibleValue.get();
            Assertions.assertThat(assertStringLiteral(aValue));
            final Literal aLiteral = ((Literal)aValue);
            Assertions.assertThat(aLiteral.label()).isEqualTo("[ \"1\"^^<http://www.w3.org/2001/XMLSchema#integer> \"2\"^^<http://www.w3.org/2001/XMLSchema#integer> ]");
            Assertions.assertThat(aResult).isExhausted();
        }
    }

    @Test
    public void happyPathComplexArray() {

        final String aQuery = sparqlPrefix +
                "select ?result where { { select (array:toString(set(?value)) as ?array) where { values ?value { 1 2 } } } unnest(?array as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
            Assertions.assertThat(aResult).hasNext();
            final Optional<Value> aPossibleValue = aResult.next().value("result");
            Assertions.assertThat(aPossibleValue).isPresent();
            final Value aValue = aPossibleValue.get();
            Assertions.assertThat(assertStringLiteral(aValue));
            final Literal aLiteral = ((Literal)aValue);
            Assertions.assertThat(aLiteral.label()).isEqualTo("[ \"1\"^^<http://www.w3.org/2001/XMLSchema#integer> \"2\"^^<http://www.w3.org/2001/XMLSchema#integer> ]");
            Assertions.assertThat(aResult).isExhausted();
        }
    }

    @Test
    public void tooManyArgs() {

        final String aQuery = sparqlPrefix +
                "select ?result where { { select (array:toString(set(?value), 5) as ?array) where { values ?value { 1 2 } } } unnest(?array as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testWrongTypeFirstArg() {

        final String aQuery = ArrayVocabulary.sparqlPrefix("array") +
            "select ?result where { { select (array:toString(5) as ?array) where { values ?value { 1 } } } unnest(?array as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }
}
