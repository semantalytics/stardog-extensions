package com.semantalytics.stardog.kibble.strings.comparison;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.*;

public class TestSorensenDiceSimilarity extends AbstractStardogTest {

    @Test
    public void testThreeArg() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?result where { bind(stringmetric:sorensenDiceSimilarity(\"ABCDE\", \"ABCDFG\", 2) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final Value aValue = aResult.next().value("result").get();

                assertThat(aValue).isInstanceOf(Literal.class);
                assertThat(Literal.floatValue((Literal)aValue)).isEqualTo(0.6666667f);
                assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
            }
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?result where { bind(stringmetric:sorensenDiceSimilarity(\"one\", \"two\", \"three\", \"four\") as ?str) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
            }
    }

    @Test
    public void testTooFewArgs() {

        final String aQuery = StringMetricVocabulary.sparqlPrefix("stringmetric") +
                    "select ?result where { bind(stringmetric:sorensenDiceSimilarity(\"one\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
            }
    }
}
