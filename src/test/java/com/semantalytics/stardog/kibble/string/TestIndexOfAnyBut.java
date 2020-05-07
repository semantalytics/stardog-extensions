package com.semantalytics.stardog.kibble.string;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class TestIndexOfAnyBut extends AbstractStardogTest {

    @Test
    public void test() {
   
            final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:indexOfAnyBut(\"stardog\", \"sdg\") AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final int aValue = Literal.intValue((Literal) aResult.next().value("result").get());

                assertThat(aValue).isEqualTo(1);
                assertThat(aResult).isExhausted();
            }
    }
  
    @Test
    public void testEmptyString() {
       
            final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:indexOfAnyBut(\"\", \"\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
        
                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final int aValue = Literal.intValue((Literal) aResult.next().value("result").get());

                assertThat(aValue).isEqualTo(-1);
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testTooFewArgs() {

            final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:indexOfAnyBut(\"one\") as ?result) }";

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
                    "select ?result where { bind(string:indexOfAnyBut(\"one\", \"two\", \"three\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
         
                assertTrue("Should have a result", aResult.hasNext());
                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testWrongTypeFirstArg() {
       
            final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:indexOfAnyBut(1, \"two\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
       
                assertTrue("Should have a result", aResult.hasNext());
                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted();
            }
    }
  
  
    @Test
    public void testWrongTypeSecondArg() {
       
            final String aQuery = StringVocabulary.sparqlPrefix("string") +
                    "select ?result where { bind(string:indexOfAnyBut(\"one\", 2) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
       
                assertTrue("Should have a result", aResult.hasNext());
                assertThat(aResult).hasNext().withFailMessage("Should have a result");

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet).isEmpty();
                assertThat(aResult).isExhausted();
            }
    }
}
