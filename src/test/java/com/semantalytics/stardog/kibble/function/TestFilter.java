package com.semantalytics.stardog.kibble.function;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.semantalytics.stardog.kibble.array.ArrayVocabulary;
import com.semantalytics.stardog.kibble.string.StringVocabulary;
import com.stardog.stark.Literal;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class TestFilter extends AbstractStardogTest {

    @Test
    public void testIriFunctionNoArgs() {

        final String aQuery = FunctionVocabulary.sparqlPrefix("func") + " " +
                StringVocabulary.sparqlPrefix("string") +
                ArrayVocabulary.sparqlPrefix("array") +
                " SELECT ?result WHERE { BIND(array:toString(func:filter(string:isNumeric, array:of(\"star\", \"dog\", \"1\", \"2\"))) AS ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            Optional<Literal> aPossibleLiteral = aResult.next().literal("result");
            assertThat(aPossibleLiteral).isPresent();
            assertThat(aPossibleLiteral.get().label()).isEqualTo("[ \"1\"^^<http://www.w3.org/2001/XMLSchema#string> \"2\"^^<http://www.w3.org/2001/XMLSchema#string> ]");
        }
    }
}
