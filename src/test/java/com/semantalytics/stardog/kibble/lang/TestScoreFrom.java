package com.semantalytics.stardog.kibble.lang;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.semantalytics.stardog.kibble.array.ArrayVocabulary;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import java.util.Optional;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;
import static org.assertj.core.api.Assertions.assertThat;

public class TestScoreFrom extends AbstractStardogTest {


    @Test
    public void detectFromEnglishOrGermanWithEnglishPhrase() {
    
        final String aQuery = LanguageVocabulary.sparqlPrefix("lang") +
                " " + ArrayVocabulary.sparqlPrefix("array") +
                    " select ?result where { bind(array:toString(lang:scoreFrom(\"good morning\", \"english\", \"german\")) AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext();
                final Optional<Value> aPossibleValue = aResult.next().value("result");
                assertThat(aPossibleValue).isPresent();
                final Value aValue = aPossibleValue.get();
                assertThat(assertStringLiteral(aValue));
                final Literal aLiteral = ((Literal)aValue);
                assertThat(aLiteral.toString()).isEqualTo("\"[ [ \"en\"^^<http://www.w3.org/2001/XMLSchema#string> \"1.0\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"de\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7357539427842713\"^^<http://www.w3.org/2001/XMLSchema#double> ] ]\"^^<http://www.w3.org/2001/XMLSchema#string>");
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void detectFromEnglishOrGermanWithGermanPhrase() {

        final String aQuery = LanguageVocabulary.sparqlPrefix("lang") +
                " " + ArrayVocabulary.sparqlPrefix("array") +
                " select ?result where { bind(array:toString(lang:scoreFrom(\"guten morgan\", \"english\", \"german\")) AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext();
            final Optional<Value> aPossibleValue = aResult.next().value("result");
            assertThat(aPossibleValue).isPresent();
            final Value aValue = aPossibleValue.get();
            assertThat(assertStringLiteral(aValue));
            final Literal aLiteral = ((Literal)aValue);
            assertThat(aLiteral.toString()).isEqualTo("\"[ [ \"de\"^^<http://www.w3.org/2001/XMLSchema#string> \"1.0\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"en\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8432153776088629\"^^<http://www.w3.org/2001/XMLSchema#double> ] ]\"^^<http://www.w3.org/2001/XMLSchema#string>");
            assertThat(aResult).isExhausted();
        }
    }

    @Test
    public void testTooFewArgs() {
    
        final String aQuery = LanguageVocabulary.sparqlPrefix("lang") +
                    "select ?result where { bind(lang:scoreFrom() as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult.hasNext()).isTrue();

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = LanguageVocabulary.sparqlPrefix("lang") +
                    "select ?result where { bind(lang:scoreFrom(\"one\", \"two\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
       
                assertThat(aResult.hasNext()).isTrue();

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testWrongTypeFirstArg() {
     
        final String aQuery = LanguageVocabulary.sparqlPrefix("lang") +
                    "select ?result where { bind(lang:scoreFrom(1) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
     
                assertThat(aResult.hasNext()).isTrue();

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }
}
