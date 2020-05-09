package com.semantalytics.stardog.kibble.string;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.assertj.core.api.Assertions.assertThat;

public class TestAbbreviateMiddle extends AbstractStardogTest {

    @Test
    public void testAbbreviateMiddle() {
      
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:abbreviateMiddle(\"Stardog graph database\", \"...\", 8) AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult.hasNext()).isTrue();

                final String aValue = aResult.next().value("result").get().toString();

                assertThat(aValue).isEqualTo("\"Sta...se\"^^<http://www.w3.org/2001/XMLSchema#string>");
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testEmptyString() {
      
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:abbreviateMiddle(\"\", \"\", 10) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
           
                assertThat(aResult.hasNext()).isTrue();

                final String aValue = aResult.next().value("result").get().toString();

                assertThat(aValue).isEqualTo("\"\"^^<http://www.w3.org/2001/XMLSchema#string>");
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testTooFewArgs() {
      
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:abbreviateMiddle(\"one\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
         
                assertThat(aResult.hasNext()).isTrue();

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:abbreviateMiddle(\"one\", 2, \"three\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult.hasNext()).isTrue();

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testWrongTypeFirstArg() {
       
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:abbreviateMiddle(4, 5) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult.hasNext()).isTrue();

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testWrongTypeSecondArg() {
     
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:abbreviateMiddle(\"one\", \"two\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult.hasNext()).isTrue();

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testLengthTooShort() {
     
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:abbreviateMiddle(\"Stardog\", 3) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
         
                assertThat(aResult.hasNext()).isTrue();

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult.hasNext()).isFalse();
            }
    }
}
