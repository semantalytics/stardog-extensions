package com.semantalytics.stardog.kibble.string.phonetic;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestDoubleMetaphone extends AbstractStardogTest {

    @Test
    public void testOneArg() {

        final String aQuery = PhoneticVocabulary.sparqlPrefix("phonetic") +
                "select ?result where { bind(phonetic:doubleMetaphone(\"Stardog\") as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final Value aValue = aResult.next().get("result");

            assertTrue(aValue instanceof Literal);

            final String aLiteralValue = ((Literal)aValue).label();

            assertEquals("STRT", aLiteralValue);
            assertFalse("Should have no more results", aResult.hasNext());
        }
    }

    @Test
    public void testTooFewAargs() {

        final String aQuery = PhoneticVocabulary.sparqlPrefix("phonetic") +
                "select ?result where { bind(phonetic:doubleMetaphone() as ?result) }";

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
                "select ?result where { bind(phonetic:doubleMetaphone(\"one\", \"two\") as ?result) }";

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
                "select ?result where { bind(phonetic:doubleMetaphone(7) as ?result) }";

        try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext().withFailMessage("Should have a result");

            final BindingSet aBindingSet = aResult.next();

            assertThat(aBindingSet).isEmpty();
            assertThat(aResult).isExhausted().withFailMessage("Should have no more results");
        }
    }
}
