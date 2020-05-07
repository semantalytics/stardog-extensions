package com.semantalytics.stardog.kibble.string;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import java.util.Optional;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;
import static org.assertj.core.api.Assertions.*;

public class TestAbbreviate extends AbstractStardogTest {

   
    @Test
    public void testAbbreviate() {
    
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:abbreviate(\"Stardog graph database\", 8) AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext();
                final Optional<Value> aPossibleValue = aResult.next().value("result");
                assertThat(aPossibleValue).isPresent();
                final Value aValue = aPossibleValue.get();
                assertThat(assertStringLiteral(aValue));
                final Literal aLiteral = ((Literal)aValue);
                assertThat(aLiteral.label()).isEqualTo("Stard...");
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testAbbreviateWithOffset() {
  
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:abbreviate(\"Stardog graph database\", 15, 5) AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult.hasNext()).isTrue();

                final String aValue = aResult.next().value("result").get().toString();

                assertThat(aValue).isEqualTo("\"...og graph ...\"^^<http://www.w3.org/2001/XMLSchema#string>");
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testEmptyString() {
  
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:abbreviate(\"\", 5) as ?result) }";

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
                    "select ?result where { bind(string:abbreviate(\"one\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult.hasNext()).isTrue();

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:abbreviate(\"one\", 9, \"three\", \"four\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
       
                assertThat(aResult.hasNext()).isTrue();

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testWrongTypeFirstArg() {
     
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:abbreviate(4, 5) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
     
                assertThat(aResult.hasNext()).isTrue();

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testWrongTypeSecondArg() {

        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:abbreviate(\"one\", \"two\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
   
                assertThat(aResult.hasNext()).isTrue();

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testWrongTypeThirdArg() {
 
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:abbreviate(\"one\", 9, \"three\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult.hasNext()).isTrue();

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testLengthTooShort() {
     
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:abbreviate(\"Stardog\", 3) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult.hasNext()).isTrue();

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }
}
