package com.semantalytics.stardog.kibble.string.phonetic;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestSoundex extends AbstractStardogTest {

    @Test
    public void testOneArg() {

        final String aQuery = PhoneticVocabulary.sparqlPrefix("phonetic") +
                "select ?result where { bind(phonetic:soundex(\"Stardog\") as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final Value aValue = aResult.next().get("result");

            assertThat(aValue).isInstanceOf(Literal.class);

            final String aLiteralValue = ((Literal)aValue).label();

            assertThat("S363").isEqualTo(aLiteralValue);
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }

    @Test
    public void testTooFewAargs() {

        final String aQuery = PhoneticVocabulary.sparqlPrefix("phonetic") +
                "select ?result where { bind(phonetic:soundex() as ?result) }";

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
                "select ?result where { bind(phonetic:soundex(\"one\", \"two\") as ?result) }";

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
                "select ?result where { bind(phonetic:soundex(7) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }
}
