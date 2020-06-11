package com.semantalytics.stardog.kibble.array;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class TestUniformDatatype extends AbstractStardogTest {

    @Test
    public void ArrayWithUniformDataTypeShouldReturnTrue() {

        final String aQuery =ArrayVocabulary.sparqlPrefix("array") +
                "select ?result where { bind(array:uniformDataType(array:of(1,2,3)) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            assertThat((aResult.next().value("result").get().toString())).isEqualTo("http://www.w3.org/2001/XMLSchema#integer");
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void ArrayWithoutUniformDataTypeShouldReturnFalse() {

        final String aQuery =ArrayVocabulary.sparqlPrefix("array") +
                "select ?result where { bind(array:uniformDataType(array:of(1,2,\"three\")) as ?result) }";

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
            "select ?result where { bind(array:uniformDataType(1) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = ArrayVocabulary.sparqlPrefix("array") +
                "select ?result where { bind(array:uniformDataType(array:of(1, 2), 3) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }
}
