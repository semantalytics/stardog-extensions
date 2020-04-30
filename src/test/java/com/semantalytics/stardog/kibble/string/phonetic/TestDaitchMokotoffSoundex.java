package com.semantalytics.stardog.kibble.string.phonetic;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestDaitchMokotoffSoundex extends AbstractStardogTest {

    @Test
    public void testOneArg() {

        final String aQuery = PhoneticVocabulary.sparqlPrefix("phonetic") +
                "select ?result where { bind(phonetic:daitchMokotoffSoundex(\"Stardog\") as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final Value aValue = aResult.next().get("result");

            assertThat(aValue).isInstanceOf(Literal.class);

            final String aLiteralValue = ((Literal)aValue).label();

            assertThat(aLiteralValue).isEqualTo("293500");
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testTooFewAargs() {

        final String aQuery = PhoneticVocabulary.sparqlPrefix("phonetic") +
                "select ?result where { bind(phonetic:daitchMokotoffSoundex() as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testTooManyAargs() {

        final String aQuery = PhoneticVocabulary.sparqlPrefix("phonetic") +
                "select ?result where { bind(phonetic:daitchMokotoffSoundex(\"one\", \"two\") as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testFirstArgWrongType() {

        final String aQuery = PhoneticVocabulary.sparqlPrefix("phonetic") +
                "select ?result where { bind(phonetic:daitchMokotoffSoundex(7) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }
}
