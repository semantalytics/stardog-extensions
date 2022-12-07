package com.semantalytics.stardog.kibble.function;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.semantalytics.stardog.kibble.string.StringVocabulary;
import com.stardog.stark.Literal;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class TestPartial extends AbstractStardogTest {

    @Test
    public void testPartial() {

        final String aQuery = FunctionVocabulary.sparqlPrefix("func") + " " +
                StringVocabulary.sparqlPrefix("string") +
                " SELECT ?result WHERE { BIND(func:call(func:partial(string:joinWith, \":\"), \"Hello\", \"world\") AS ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            Optional<Literal> aPossibleLiteral = aResult.next().literal("result");
            assertThat(aPossibleLiteral).isPresent();
            assertThat(aPossibleLiteral.get().label()).isEqualTo("Hello:world");
        }
    }
}
