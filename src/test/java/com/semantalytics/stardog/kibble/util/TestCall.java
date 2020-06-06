package com.semantalytics.stardog.kibble.util;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestCall extends AbstractStardogTest {

    final static String sparqlPrefix = UtilVocabulary.sparqlPrefix("util");

    @Test
    public void testCall() {

            final String aQuery = sparqlPrefix
                    + "select ?result where { bind(util:call(\"http://semantalytics.com/2017/09/ns/stardog/kibble/string/capitalize\", \"test\") as ?result) } ";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final int aValue = Literal.intValue(aResult.next().literal("result").get());

                assertThat(aValue).isEqualTo(3);
                assertThat(aResult).isExhausted().withFailMessage("Should have no more results");

            }
    }
}
