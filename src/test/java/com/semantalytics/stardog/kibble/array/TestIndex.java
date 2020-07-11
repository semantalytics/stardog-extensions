package com.semantalytics.stardog.kibble.array;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class TestIndex extends AbstractStardogTest {

    @Test
    public void testTwoArgOffset() {

        final String aQuery =ArrayVocabulary.sparqlPrefix("array") +
                "select (array:index(?result, array:offset(0)) as ?resultIndex0) (array:index(?result, array:offset(1)) as ?resultIndex1) where { bind(array:of(7, 9) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final BindingSet bindings = aResult.next();
            final Optional<Literal> aPossibleLiteral0 = bindings.literal("resultIndex0");
            assertThat(aPossibleLiteral0).isPresent();
            final int index0 = Literal.intValue(aPossibleLiteral0.get());
            Optional<Literal> aPossibleLiteral1 = bindings.literal("resultIndex1");
            assertThat(aPossibleLiteral1).isPresent();
            final int index1 = Literal.intValue(aPossibleLiteral1.get());
            assertThat(index0).isEqualTo(7);
            assertThat(index1).isEqualTo(9);
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testTwoArgOrdinal() {

        final String aQuery =ArrayVocabulary.sparqlPrefix("array") +
                "select (array:index(?result, array:ordinal(1)) as ?resultIndex0) (array:index(?result, array:ordinal(2)) as ?resultIndex1) where { bind(array:of(7, 9) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final BindingSet bindings = aResult.next();
            final Optional<Literal> aPossibleLiteral0 = bindings.literal("resultIndex0");
            assertThat(aPossibleLiteral0).isPresent();
            final int index0 = Literal.intValue(aPossibleLiteral0.get());
            Optional<Literal> aPossibleLiteral1 =bindings.literal("resultIndex1");
            assertThat(aPossibleLiteral1).isPresent();
            final int index1 = Literal.intValue(aPossibleLiteral1.get());
            assertThat(index0).isEqualTo(7);
            assertThat(index1).isEqualTo(9);
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testWrongTypeFirstArg() {

        final String aQuery = ArrayVocabulary.sparqlPrefix("array") +
            "select (array:index(?array, array:offset(0)) as ?result) where { bind(array:size(7, 8) as ?array) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void tooManyArgumentsShouldNotReturnResults() {

        final String aQuery = ArrayVocabulary.sparqlPrefix("array") +
                "select (array:index(?array, array:offset(0), 1) as ?result) where { bind(array:size(7, 8) as ?array) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void ordinalLowerIndexOutOfBoundsShouldNotReturnResults() {

        final String aQuery = ArrayVocabulary.sparqlPrefix("array") +
                "select (array:index(?array, array:ordinal(0)) as ?result) where { bind(array:size(7, 8) as ?array) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void ordinalUpperIndexOutOfBoundsShouldNotReturnResults() {

        final String aQuery = ArrayVocabulary.sparqlPrefix("array") +
                "select (array:index(?array, array:ordinal(3)) as ?result) where { bind(array:size(7, 8) as ?array) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void offsetLowerIndexOutOfBoundsShouldNotReturnResults() {

        final String aQuery = ArrayVocabulary.sparqlPrefix("array") +
            "select (array:index(?array, array:offset(-1)) as ?result) where { bind(array:size(7, 8) as ?array) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void offsetUpperIndexOutOfBoundsShouldNotReturnResults() {

        final String aQuery = ArrayVocabulary.sparqlPrefix("array") +
                "select (array:index(?array, array:offset(2)) as ?result) where { bind(array:size(7, 8) as ?array) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }
}
