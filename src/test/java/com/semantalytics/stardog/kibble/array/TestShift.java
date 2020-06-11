package com.semantalytics.stardog.kibble.array;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.apache.commons.collections4.IteratorUtils;
import org.junit.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class TestShift extends AbstractStardogTest {

    @Test
    public void testOneArg() {

        final String aQuery =ArrayVocabulary.sparqlPrefix("array") +
                "select ?result where { unnest(array:shift(array:of(1, 2, 3, 4, 5), 1) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            List<Integer> results = IteratorUtils.toList(aResult).stream().map(b -> b.get("result")).filter(Literal.class::isInstance).map(Literal.class::cast).map(Literal::intValue).collect(toList());
            assertThat(results).containsExactly(5, 1, 2, 3, 4);
        }
    }

    @Test
    public void testWrongTypeSecondArg() {

        final String aQuery = ArrayVocabulary.sparqlPrefix("array") +
            "select ?result where { unnest(array:shift(array:of(1, 2, 3, 4, 5), \"1\") as ?result) }";


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
            "select ?result where { unnest(array:shift(1, 1) as ?result) }";


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
            "select ?result where { unnest(array:shift(array:of(1, 2, 3, 4, 5), 1, 2) as ?result) }";


        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }
}
