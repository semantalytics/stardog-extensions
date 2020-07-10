package com.semantalytics.stardog.kibble.crypto;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import java.util.Optional;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;
import static org.assertj.core.api.Assertions.assertThat;

public class TestFormatPreservingEncryptionFunction extends AbstractStardogTest {

   
    @Test
    public void testEncryptDecrypt() {

        final String plainText = "abcdef";
        final String cypherText;

        final String aEncryptQuery = CryptoVocabulary.sparqlPrefix("crypto") +
                    " select ?result where { bind(crypto:formatPreservingEncrypt(\"aGR5ZTMzamRoZHllMzNqZA==\", \"" + plainText + "\", \"\") AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aEncryptQuery).execute()) {

                assertThat(aResult).hasNext();
                final Optional<Value> aPossibleValue = aResult.next().value("result");
                assertThat(aPossibleValue).isPresent();
                final Value aValue = aPossibleValue.get();
                assertThat(assertStringLiteral(aValue));
                final Literal aLiteral = ((Literal)aValue);
                assertThat(aLiteral.label()).isNotEqualTo(plainText);
                assertThat(aLiteral.label()).hasSameSizeAs(plainText);
                assertThat(aResult).isExhausted();
                cypherText = aLiteral.label();
                System.out.println("Encrypted to " + cypherText);
            }

        final String aDecryptQuery = CryptoVocabulary.sparqlPrefix("crypto") +
                " select ?result where { bind(crypto:formatPreservingDecrypt(\"aGR5ZTMzamRoZHllMzNqZA==\", \"" + cypherText + "\", \"\") AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aDecryptQuery).execute()) {

            assertThat(aResult).hasNext();
            final Optional<Value> aPossibleValue = aResult.next().value("result");
            assertThat(aPossibleValue).isPresent();
            final Value aValue = aPossibleValue.get();
            assertThat(assertStringLiteral(aValue));
            final Literal aLiteral = ((Literal)aValue);
            assertThat(aLiteral.label()).isNotEqualTo(cypherText);
            assertThat(aLiteral.label()).isEqualTo(plainText);
            assertThat(aLiteral.label()).hasSameSizeAs(cypherText);
            assertThat(aLiteral.label()).isNotEqualTo(cypherText);
            assertThat(aResult).isExhausted();
            System.out.println("Decrypted back to " + aLiteral.label());
        }

    }
}
