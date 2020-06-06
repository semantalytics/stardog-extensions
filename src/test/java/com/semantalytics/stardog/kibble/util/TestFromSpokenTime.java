package com.semantalytics.stardog.kibble.util;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.assertj.core.api.Assertions.assertThat;

public class TestFromSpokenTime extends AbstractStardogTest {

    final static String sparqlPrefix = UtilVocabulary.sparqlPrefix("util");

    @Test
    public void testFromSpokenTime() {

            final String aQuery = sparqlPrefix
                    + "select ?result where { bind(util:fromSpokenTime(\"next week\") as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult.hasNext()).isTrue().withFailMessage("Should have a result");

                final long aValue = Literal.longValue(aResult.next().literal("result").get());

                assertThat(aValue).isEqualTo(3);

                assertThat(aResult.hasNext()).isFalse().withFailMessage("Should have no more results");
            }
    }

}