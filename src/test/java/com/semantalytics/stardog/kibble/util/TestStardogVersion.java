package com.semantalytics.stardog.kibble.util;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class TestStardogVersion  extends AbstractStardogTest {

    final static String sparqlPrefix = UtilVocabulary.sparqlPrefix("util");

    @Test
    public void testStardogVersion() {

            final String aQuery = sparqlPrefix
                    + "select ?result where { bind(util:stardogVersion() AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final Optional<Literal> aPossibleLiteral = aResult.next().literal("result");
                assertThat(aPossibleLiteral).isPresent();
                final Literal aLiteral = aPossibleLiteral.get();
                assertThat(aLiteral.label()).isEqualTo("7.3.0");

                assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
            }
    }
}
