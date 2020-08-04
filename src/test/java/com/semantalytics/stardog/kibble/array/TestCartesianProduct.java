package com.semantalytics.stardog.kibble.array;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.apache.commons.collections4.IteratorUtils;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class TestCartesianProduct extends AbstractStardogTest {

    @Test
    public void testTwoArg() {

        final String aQuery =ArrayVocabulary.sparqlPrefix("array") +
                "select (array:index(?result, array:offset(0)) as ?resultIndex0) (array:index(?result, array:offset(1)) as ?resultIndex1)  where { unnest(array:cartesianProduct(array:of(1, 2), array:of(3, 4)) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            final List<BindingSet> results = IteratorUtils.toList(aResult);
            assertThat(results).hasSize(4);
            int[] index0Results = results.stream().map(b -> b.literal("resultIndex0")).filter(Optional::isPresent).map(Optional::get).mapToInt(Literal::intValue).toArray();
            int[] index1Results = results.stream().map(b -> b.literal("resultIndex1")).filter(Optional::isPresent).map(Optional::get).mapToInt(Literal::intValue).toArray();
            assertThat(index0Results).containsExactly(1, 1, 2, 2);
            assertThat(index1Results).containsExactly(3, 4, 3, 4);
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testWrongTypeFirstArg() {

        final String aQuery = ArrayVocabulary.sparqlPrefix("array") +
                "select ?result where { bind(array:cartesianProduct(1, array:of(2, 3)) as ?result) }";

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
                "select ?result where { bind(array:cartesianProduct(array:of(1, 2), 3) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testWrongTypeBothArgs() {

        final String aQuery = ArrayVocabulary.sparqlPrefix("array") +
                "select ?result where { bind(array:cartesianProduct(1, 2) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }
}
