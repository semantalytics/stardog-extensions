package com.semantalytics.stardog.kibble.array;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class TestZip extends AbstractStardogTest {

    @Test
    public void testTwoArg() {

        final String aQuery =ArrayVocabulary.sparqlPrefix("array") +
                "select (array:index(?result, array:ordinal(1)) as ?resultIndex1) (array:index(?result, array:ordinal(2)) as ?resultIndex2) where { unnest(array:zip(array:of(1, 2), array:of(3, 4)) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet1 = aResult.next();

            final Optional<Literal> aPossibleLiteral11 = aBindingSet1.literal("resultIndex1");
            AssertionsForClassTypes.assertThat(aPossibleLiteral11).isPresent();
            final Literal aLiteral11 = aPossibleLiteral11.get();
            assertThat(Literal.intValue(aLiteral11)).isEqualTo(1);

            final Optional<Literal> aPossibleLiteral12 = aBindingSet1.literal("resultIndex2");
            AssertionsForClassTypes.assertThat(aPossibleLiteral12).isPresent();
            final Literal aLiteral12 = aPossibleLiteral12.get();
            assertThat(Literal.intValue(aLiteral12)).isEqualTo(3);

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final BindingSet aBindingSet2 = aResult.next();

            final Optional<Literal> aPossibleLiteral21 = aBindingSet2.literal("resultIndex1");
            AssertionsForClassTypes.assertThat(aPossibleLiteral21).isPresent();
            final Literal aLiteral21 = aPossibleLiteral21.get();
            assertThat(Literal.intValue(aLiteral21)).isEqualTo(2);

            final Optional<Literal> aPossibleLiteral22 = aBindingSet2.literal("resultIndex2");
            AssertionsForClassTypes.assertThat(aPossibleLiteral22).isPresent();
            final Literal aLiteral22 = aPossibleLiteral22.get();
            assertThat(Literal.intValue(aLiteral22)).isEqualTo(4);

            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testWrongTypeFirstArg() {

        final String aQuery = ArrayVocabulary.sparqlPrefix("array") +
        "select (array:index(?result, array:ordinal(1)) as ?resultIndex1) (array:index(?result, array:ordinal(2)) as ?resultIndex2) where { unnest(array:zip(1, array:of(3, 4)) as ?result) }";

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
                "select (array:index(?result, array:ordinal(1)) as ?resultIndex1) (array:index(?result, array:ordinal(2)) as ?resultIndex2) where { unnest(array:zip(array:of(1, 2), 3) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testArraysNotSameLength() {

        final String aQuery = ArrayVocabulary.sparqlPrefix("array") +
                "select (array:index(?result, array:ordinal(1)) as ?resultIndex1) (array:index(?result, array:ordinal(2)) as ?resultIndex2) where { unnest(array:zip(array:of(1, 2, 3), array:of(3, 4)) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }
}
