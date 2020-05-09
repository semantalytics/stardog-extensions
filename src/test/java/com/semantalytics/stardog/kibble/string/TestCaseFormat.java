package com.semantalytics.stardog.kibble.string;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import java.util.Optional;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class TestCaseFormat extends AbstractStardogTest {
 
    @Test
    public void testLowerCamelToUpperUnderscoreByExample() {
       
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:caseFormat(\"stardogUnion\", \"fromFormat\", \"TO_FORMAT\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext().withFailMessage("Should have a result");
                final Optional<Value> aPossibleValue = aResult.next().value("result");
                assertThat(aPossibleValue).isPresent();
                final Value aValue = aPossibleValue.get();
                assertThat(assertStringLiteral(aValue));
                final Literal aLiteral = (Literal) aValue;

                assertThat(aLiteral.label()).isEqualTo("STARDOG_UNION");
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testLowerCamelToLowerUnderscoreByExample() {
     
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:caseFormat(\"stardogUnion\", \"fromFormat\", \"to_format\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext().withFailMessage("Should have a result");
                final Optional<Value> aPossibleValue = aResult.next().value("result");
                assertThat(aPossibleValue).isPresent();
                final Value aValue = aPossibleValue.get();
                assertThat(assertStringLiteral(aValue));
                final Literal aLiteral = (Literal) aValue;
                assertThat(aLiteral.label()).isEqualTo("stardog_union");
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testLowerCamelToLowerHyphenByExample() {
       
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:caseFormat(\"stardogUnion\", \"fromFormat\", \"to-format\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
           
                assertThat(aResult).hasNext().withFailMessage("Should have a result");
                final Optional<Value> aPossibleValue = aResult.next().value("result");
                assertThat(aPossibleValue).isPresent();
                final Value aValue = aPossibleValue.get();
                assertThat(assertStringLiteral(aValue));
                final Literal aLiteral = (Literal) aValue;
                assertThat(aLiteral.label()).isEqualTo("stardog-union");
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testLowerCamelToUpperCamelByExample() {
    
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:caseFormat(\"stardogUnion\", \"fromFormat\", \"ToFormat\") as ?result) }";

            try(final SelectQueryResult theCollectionOfResults = connection.select(aQuery).execute()) {

                assertThat(theCollectionOfResults).hasNext().withFailMessage("Should have a result");
                final Optional<Value> aPossibleValue = theCollectionOfResults.next().value("result");
                assertThat(aPossibleValue).isPresent();
                final Value aValue = aPossibleValue.get();
                assertThat(assertStringLiteral(aValue));
                final String theResult = ((Literal) aValue).label();

                assertThat(theResult).isEqualTo("StardogUnion");
                assertThat(theCollectionOfResults).isExhausted();
            }
    }

    @Test
    public void testTooManyArgs() {
      
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?caseFormat where { bind(string:caseFormat(\"one\", \"two\", \"three\", \"four\") as ?caseFormat) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

    @Test
    public void testWrongType() {
    
        final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?caseFormat where { bind(string:caseFormat(7, 8, 9) as ?caseFormat) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertTrue("Should have a result", aResult.hasNext());

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }
}
