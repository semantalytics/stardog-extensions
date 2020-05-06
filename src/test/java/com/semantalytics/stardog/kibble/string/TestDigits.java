package com.semantalytics.stardog.kibble.string;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class TestDigits extends AbstractStardogTest {

    @Test
    public void testGetDigits() {
   
            final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:digits(\"Stard0g\") AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext().withFailMessage("Should have a result");
                final Value aValue = aResult.next().value("result").get();

                assertThat(((Literal)aValue).label()).isEqualTo("0");
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testOnlyDigits() {
       

            final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:digits(\"12345\") AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext().withFailMessage("Should have a result");


                final Value aValue = aResult.next().value("result").get();

                assertThat(((Literal)aValue).label()).isEqualTo("12345");
                assertThat(aResult).isExhausted();
            }
    }
  
    @Test
    public void testEmptyString() {
       
            final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:digits(\"\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
        
                assertThat(aResult).hasNext().withFailMessage("Should have a result");


                final Value aValue = aResult.next().value("result").get();

                assertThat(((Literal)aValue).label()).isEqualTo("");
                assertThat(aResult).isExhausted();
            }
    }
  
    @Test
    public void testNonAscii() {
       
            final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:digits(\"१२३\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

        
                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final Value aValue = aResult.next().value("result").get();

                assertThat(((Literal)aValue).label()).isEqualTo("१२३");
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testTooFewArgs() {

       
            final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:digits() as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
          
                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testTooManyArgs() {

     
            final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:digits(\"Stardog\", \"one\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
         
                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testWrongType() {
       
            final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:digits(4) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
       
                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted();
            }
    }
}
