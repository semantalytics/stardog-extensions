package com.semantalytics.stardog.kibble.function;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.semantalytics.stardog.kibble.string.StringVocabulary;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class TestCall extends AbstractStardogTest {

    @Test
    public void testTwoArg() {

        final String aQuery = String.format(FunctionVocabulary.sparqlPrefix("func")
         + " SELECT ?result WHERE { BIND(func:call(\"%s\", \"Hello world\" ) AS ?result) }", StringVocabulary.capitalize);

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            assertThat(aResult.next().literal("result").get().label()).isEqualTo("Hello World");
        }
    }
}
