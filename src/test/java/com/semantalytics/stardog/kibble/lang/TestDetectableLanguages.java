package com.semantalytics.stardog.kibble.lang;

import com.semantalytics.stardog.kibble.AbstractStardogTest;
import com.stardog.stark.Literal;
import com.stardog.stark.Value;
import com.stardog.stark.query.BindingSet;
import com.stardog.stark.query.SelectQueryResult;
import org.junit.Test;

import java.util.Optional;

import static com.complexible.stardog.plan.filter.functions.AbstractFunction.assertStringLiteral;
import static org.assertj.core.api.Assertions.assertThat;

public class TestDetectableLanguages extends AbstractStardogTest {


    @Test
    public void testDetectableLanguages() {
    
        final String aQuery = LanguageVocabulary.sparqlPrefix("lang") +
                    "select ?result where { bind(lang:detectableLanguages() AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext();
                final Optional<Value> aPossibleValue = aResult.next().value("result");
                assertThat(aPossibleValue).isPresent();
                final Value aValue = aPossibleValue.get();
                assertThat(assertStringLiteral(aValue));
                final Literal aLiteral = ((Literal)aValue);
                assertThat(aLiteral.label()).isEqualTo("AFRIKAANS ,ALBANIAN ,ARABIC ,ARMENIAN ,AZERBAIJANI ,BASQUE ,BELARUSIAN ,BENGALI ,BOKMAL ,BOSNIAN ,BULGARIAN ,CATALAN ,CHINESE ,CROATIAN ,CZECH ,DANISH ,DUTCH ,ENGLISH ,ESPERANTO ,ESTONIAN ,FINNISH ,FRENCH ,GANDA ,GEORGIAN ,GERMAN ,GREEK ,GUJARATI ,HEBREW ,HINDI ,HUNGARIAN ,ICELANDIC ,INDONESIAN ,IRISH ,ITALIAN ,JAPANESE ,KAZAKH ,KOREAN ,LATIN ,LATVIAN ,LITHUANIAN ,MACEDONIAN ,MALAY ,MARATHI ,MONGOLIAN ,NYNORSK ,PERSIAN ,POLISH ,PORTUGUESE ,PUNJABI ,ROMANIAN ,RUSSIAN ,SERBIAN ,SHONA ,SLOVAK ,SLOVENE ,SOMALI ,SOTHO ,SPANISH ,SWAHILI ,SWEDISH ,TAGALOG ,TAMIL ,TELUGU ,THAI ,TSONGA ,TSWANA ,TURKISH ,UKRAINIAN ,URDU ,VIETNAMESE ,WELSH ,XHOSA ,YORUBA ,ZULU ,UNKNOWN");
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void testTooManyArgs() {

        final String aQuery = LanguageVocabulary.sparqlPrefix("lang") +
                    "select ?result where { bind(lang:detectableLanguages(\"one\") as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
       
                assertThat(aResult.hasNext()).isTrue();

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }

}
