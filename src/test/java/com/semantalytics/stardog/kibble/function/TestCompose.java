package com.semantalytics.stardog.kibble.function;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.semantalytics.stardog.kibble.string.StringVocabulary;
import com.stardog.stark.Literal;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class TestCompose extends AbstractStardogTest {

    @Test
    public void testIriFunctionNoArgs() {

        final String aQuery = FunctionVocabulary.sparqlPrefix("func") + " " +
                StringVocabulary.sparqlPrefix("string") +
                " SELECT ?result WHERE { BIND(func:call(func:compose(string:reverse, string:upperCase), \"Hello world\") AS ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            Optional<Literal> aPossibleLiteral = aResult.next().literal("result");
            assertThat(aPossibleLiteral).isPresent();
            assertThat(aPossibleLiteral.get().label()).isEqualTo("DLROW OLLEH");
        }
    }

    @Test
    public void testIriFunction() {

        final String aQuery = FunctionVocabulary.sparqlPrefix("func") + " "
                + StringVocabulary.sparqlPrefix("string")
                + " SELECT ?result WHERE { BIND(func:call(string:upperCase, \"Hello world\" ) AS ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            Optional<Literal> aPossibleLiteral = aResult.next().literal("result");
            assertThat(aPossibleLiteral).isPresent();
            assertThat(aPossibleLiteral.get().label()).isEqualTo("HELLO WORLD");
        }
    }

    @Test
    public void testStringFunction() {

        final String aQuery = String.format(FunctionVocabulary.sparqlPrefix("func")
                + " SELECT ?result WHERE { BIND(func:call(\"%s\", \"Hello world\" ) AS ?result) }", StringVocabulary.upperCase);

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            Optional<Literal> aPossibleLiteral = aResult.next().literal("result");
            assertThat(aPossibleLiteral).isPresent();
            assertThat(aPossibleLiteral.get().label()).isEqualTo("HELLO WORLD");
        }
    }

    @Test
    public void testStringFunctionAsArg() {

        final String aQuery = FunctionVocabulary.sparqlPrefix("func") + " "
                + StringVocabulary.sparqlPrefix("string")
                + " SELECT ?result WHERE { BIND(string:reverse(func:call(string:upperCase, \"Hello world\" )) AS ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            Optional<Literal> aPossibleLiteral = aResult.next().literal("result");
            assertThat(aPossibleLiteral).isPresent();
            assertThat(aPossibleLiteral.get().label()).isEqualTo("DLROW OLLEH");
        }
    }
}
