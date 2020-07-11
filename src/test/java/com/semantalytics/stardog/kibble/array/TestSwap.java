package com.semantalytics.stardog.kibble.array;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class TestSwap extends AbstractStardogTest {

    @Test
    public void testThreeArg() {

        final String aQuery =ArrayVocabulary.sparqlPrefix("array") +
                "select (array:index(?result, array:ordinal(1)) as ?resultIndex1) (array:index(?result, array:ordinal(2)) as ?resultIndex2) where { bind(array:swap(array:of(1, 2), array:offset(0), array:offset(1)) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final BindingSet bindingSet = aResult.next();

            final Optional<Literal> aPossibleLiteral1 = bindingSet.literal("resultIndex1");
            assertThat(aPossibleLiteral1).isPresent();
            assertThat(Literal.intValue(aPossibleLiteral1.get())).isEqualTo(2);

            final Optional<Literal> aPossibleLiteral2 = bindingSet.literal("resultIndex2");
            assertThat(aPossibleLiteral2).isPresent();
            assertThat(Literal.intValue(aPossibleLiteral2.get())).isEqualTo(1);

            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testWrongTypeFirstArg() {

        final String aQuery = ArrayVocabulary.sparqlPrefix("array") +
                "select (array:index(?result, array:ordinal(1)) as ?resultIndex1) (array:index(?result, array:ordinal(2)) as ?resultIndex2) where { bind(array:swap(1, 2, 3) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testWrongTypeSecondArg() {

        final String aQuery = ArrayVocabulary.sparqlPrefix("array") +
                "select (array:index(?result, array:ordinal(1)) as ?resultIndex1) (array:index(?result, array:ordinal(2)) as ?resultIndex2) where { bind(array:swap(array:of(1, 2), \"A\", 1) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testWrongTypeThirdArg() {

        final String aQuery = ArrayVocabulary.sparqlPrefix("array") +
                "select (array:index(?result, array:ordinal(1)) as ?resultIndex1) (array:index(?result, array:ordinal(2)) as ?resultIndex2) where { bind(array:swap(array:of(1, 2), 2, \"A\") as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testTooMayArgs() {

        final String aQuery = ArrayVocabulary.sparqlPrefix("array") +
                "select (array:index(?result, array:ordinal(1)) as ?resultIndex1) (array:index(?result, array:ordinal(2)) as ?resultIndex2) where { bind(array:swap(array:of(1, 2), 0, 1, 1) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }
}
