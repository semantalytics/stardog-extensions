package com.semantalytics.stardog.kibble.util;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestSayOrdinal extends AbstractStardogTest {

    final static String sparqlPrefix = UtilVocabulary.sparqlPrefix("util");

    @Test
    public void testSayOrdinal() {

            final String aQuery = sparqlPrefix
                    + "select ?result where { bind(util:sayOrdinal(1) as ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult.hasNext()).isTrue().withFailMessage("Should have a result");

                final String aValue = aResult.next().literal("result").get().label();

                assertThat(aValue).isEqualTo("");

                assertThat(aResult.hasNext()).isFalse().withFailMessage("Should have no more results");
            }
    }

}
