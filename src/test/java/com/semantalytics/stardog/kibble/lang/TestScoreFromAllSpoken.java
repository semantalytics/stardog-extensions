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

public class TestScoreFromAllSpoken extends AbstractStardogTest {


    @Test
    public void scoreFromAllSpokenWithEnglishPhrase() {
    
        final String aQuery = LanguageVocabulary.sparqlPrefix("lang") +
                " " + ArrayVocabulary.sparqlPrefix("array") +
                    " select ?result where { bind(array:toString(lang:scoreFromAllSpoken(\"Stardog graph database\")) AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext();
                final Optional<Value> aPossibleValue = aResult.next().value("result");
                assertThat(aPossibleValue).isPresent();
                final Value aValue = aPossibleValue.get();
                assertThat(assertStringLiteral(aValue));
                final Literal aLiteral = ((Literal)aValue);
                assertThat(aLiteral.toString()).isEqualTo("\"[ [ \"en\"^^<http://www.w3.org/2001/XMLSchema#string> \"1.0\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"xh\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9931761961991691\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"zu\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9876940738834004\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"st\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9423036180665316\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"cy\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9416488877141259\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"tl\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9375942963046059\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"yo\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9310529103540938\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"nl\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9269961444869173\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"nn\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9203668214178451\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"eo\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9117336960165795\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"da\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8997526288480043\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"es\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8948822965167095\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"sq\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8926864807925837\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"it\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8917792778310925\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"nb\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8905167742576386\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"fr\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8895994584961072\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"sv\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8837636980105033\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"sw\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8830906867522164\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"de\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8824703975078757\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"af\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8735652494727943\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"cs\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8550290526529672\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"ts\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8547279563310194\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"id\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8522964722735535\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"tr\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8509583340549669\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"et\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8490859628297636\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"sk\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8485699492874365\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"sn\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8470114731945269\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"bs\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8453888106781057\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"tn\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8435959233060394\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"hu\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8409159235878121\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"sl\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8325652818196269\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"hr\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8295503557288567\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"lt\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.828352318923013\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"vi\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8236668514977921\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"eu\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8211794041957317\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"lg\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8173522969761938\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"pt\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8165695673636714\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"pl\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8111423133627907\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"ca\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8085740240488392\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"is\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8038962313101736\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"ga\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8030319163788217\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"so\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7995035248249532\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"ro\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7879870275874677\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"ms\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7656717042608517\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"fi\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.764950077067241\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"lv\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7648323183531777\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"az\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7356551376963827\"^^<http://www.w3.org/2001/XMLSchema#double> ] ]\"^^<http://www.w3.org/2001/XMLSchema#string>");
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void scoreFromAllSpokenWithLatinPhrase() {

        final String aQuery = LanguageVocabulary.sparqlPrefix("lang") +
                " " + ArrayVocabulary.sparqlPrefix("array") +
                "select ?result where { bind(array:toString(lang:scoreFromAllSpoken(\"bonum mane\")) AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext();
            final Optional<Value> aPossibleValue = aResult.next().value("result");
            assertThat(aPossibleValue).isPresent();
            final Value aValue = aPossibleValue.get();
            assertThat(assertStringLiteral(aValue));
            final Literal aLiteral = ((Literal)aValue);
            assertThat(aLiteral.toString()).isEqualTo("\"[ [ \"tn\"^^<http://www.w3.org/2001/XMLSchema#string> \"1.0\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"ro\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8596504481943643\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"sq\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.85783774959597\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"nn\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8415430597439845\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"lt\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8379395019792493\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"tr\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.833330246961796\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"st\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.825362479075685\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"nb\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8221235583429678\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"en\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8141272459051551\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"fi\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8087853815900347\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"da\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8069295801798759\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"it\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.806427171000646\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"is\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7989703348376914\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"es\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.792026261364942\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"fr\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7887756384558317\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"lg\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.787291957198325\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"lv\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7871817084651198\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"tl\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7837341119537046\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"nl\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7814877790590311\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"af\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7807346601820395\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"bs\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7757056237658037\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"eu\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7753929557393693\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"sn\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7698161428636785\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"ca\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7686417203011543\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"de\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7661037186469112\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"cs\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7644241460573005\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"pt\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7642194170928388\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"id\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7628344214615295\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"ms\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7605965913105884\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"pl\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7605219745707245\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"eo\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7590786044977316\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"sl\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7542278505144423\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"sv\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7534976245830063\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"xh\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7516366467064255\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"sw\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7500135519145339\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"hr\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7492211109033223\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"et\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7442033523456963\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"yo\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7428776205494291\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"az\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7403026414604853\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"zu\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7339977900815218\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"sk\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7288558781119022\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"cy\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.708078949144959\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"hu\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.6975968822983966\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"ts\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.6776978270219266\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"ga\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.653621564216496\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"so\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.6358923368664792\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"vi\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.5708944391731116\"^^<http://www.w3.org/2001/XMLSchema#double> ] ]\"^^<http://www.w3.org/2001/XMLSchema#string>");
            assertThat(aResult).isExhausted();
        }
    }

    @Test
    public void testTooFewArgs() {
    
        final String aQuery = LanguageVocabulary.sparqlPrefix("lang") +
                    "select ?result where { bind(lang:scoreFromAllSpoken() as ?result) }";

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
                    "select ?result where { bind(lang:scoreFromAllSpoken(\"one\", \"two\") as ?result) }";

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
                    "select ?result where { bind(lang:scoreFromAllSpoken(1) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
     
                assertThat(aResult.hasNext()).isTrue();

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }
}
