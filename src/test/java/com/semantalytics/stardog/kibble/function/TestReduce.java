package com.semantalytics.stardog.kibble.function;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.semantalytics.stardog.kibble.array.ArrayVocabulary;
import com.semantalytics.stardog.kibble.string.StringVocabulary;
import com.stardog.stark.Literal;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class TestReduce extends AbstractStardogTest {

    @Test
    public void sumOverThreeInts() {

        final String aQuery = FunctionVocabulary.sparqlPrefix("func") + " " +
                StringVocabulary.sparqlPrefix("string") + " " +
                ArrayVocabulary.sparqlPrefix("array") +
                " SELECT ?result WHERE { BIND(func:reduce(\"http://www.w3.org/2005/xpath-functions#numeric-add\", array:of(2, 2, 2)) AS ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            Optional<Literal> aPossibleLiteral = aResult.next().literal("result");
            assertThat(aPossibleLiteral).isPresent();
            assertThat(Literal.intValue(aPossibleLiteral.get())).isEqualTo(6);
        }
    }

    @Test
    public void sumOverTwoInts() {

        final String aQuery = FunctionVocabulary.sparqlPrefix("func") + " " +
                StringVocabulary.sparqlPrefix("string") + " " +
                ArrayVocabulary.sparqlPrefix("array") +
                " SELECT ?result WHERE { BIND(func:reduce(\"http://www.w3.org/2005/xpath-functions#numeric-add\", array:of(2, 2)) AS ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            Optional<Literal> aPossibleLiteral = aResult.next().literal("result");
            assertThat(aPossibleLiteral).isPresent();
            assertThat(Literal.intValue(aPossibleLiteral.get())).isEqualTo(4);
        }
    }

    @Test
    public void tooFewArrayLiterals() {

        final String aQuery = FunctionVocabulary.sparqlPrefix("func") + " " +
                StringVocabulary.sparqlPrefix("string") + " " +
                ArrayVocabulary.sparqlPrefix("array") +
                " SELECT ?result WHERE { BIND(func:reduce(\"http://www.w3.org/2005/xpath-functions#numeric-add\", array:of(2)) AS ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            Optional<Literal> aPossibleLiteral = aResult.next().literal("result");
            assertThat(aPossibleLiteral).isNotPresent();
            assertThat(aResult).isExhausted();
        }
    }

    @Test
    public void wrongTypeFirstArg() {

        final String aQuery = FunctionVocabulary.sparqlPrefix("func") + " " +
                StringVocabulary.sparqlPrefix("string") + " " +
                ArrayVocabulary.sparqlPrefix("array") +
                " SELECT ?result WHERE { BIND(func:reduce(1, array:of(2, 2)) AS ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            Optional<Literal> aPossibleLiteral = aResult.next().literal("result");
            assertThat(aPossibleLiteral).isNotPresent();
            assertThat(aResult).isExhausted();
        }
    }

    @Test
    public void wrongTypeSecondArg() {

        final String aQuery = FunctionVocabulary.sparqlPrefix("func") + " " +
                StringVocabulary.sparqlPrefix("string") + " " +
                ArrayVocabulary.sparqlPrefix("array") +
                " SELECT ?result WHERE { BIND(func:reduce(\"http://www.w3.org/2005/xpath-functions#numeric-add\", 2) AS ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");
            Optional<Literal> aPossibleLiteral = aResult.next().literal("result");
            assertThat(aPossibleLiteral).isNotPresent();
            assertThat(aResult).isExhausted();
        }
    }
}
