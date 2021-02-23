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

public class TestScoreFromAllExcept extends AbstractStardogTest {


    @Test
    public void scoreAllExceptEnglishWithEnglishPhrase() {
    
        final String aQuery = LanguageVocabulary.sparqlPrefix("lang") +
                " " + ArrayVocabulary.sparqlPrefix("array") +
                    " select ?result where { bind(array:toString(lang:scoreFromAllExcept(\"Stardog graph database\", \"english\")) AS ?result) }";

            try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

                assertThat(aResult).hasNext();
                final Optional<Value> aPossibleValue = aResult.next().value("result");
                assertThat(aPossibleValue).isPresent();
                final Value aValue = aPossibleValue.get();
                assertThat(assertStringLiteral(aValue));
                final Literal aLiteral = ((Literal)aValue);
                assertThat(aLiteral.toString()).isEqualTo("\"[ [ \"xh\"^^<http://www.w3.org/2001/XMLSchema#string> \"1.0\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"zu\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9944802117320689\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"st\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9487778922538378\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"cy\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9481186634534383\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"tl\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9440362142112627\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"yo\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9374498844386145\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"nl\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9333652457987623\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"la\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9292510767181843\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"nn\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9266903747190464\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"eo\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9179979338064429\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"da\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9059345484631111\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"es\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9010307535977755\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"sq\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8988198511088425\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"it\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.897906415038825\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"nb\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8966352371971837\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"fr\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8957116188452318\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"sv\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8898357626699257\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"sw\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8891581273612437\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"de\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8885335763030181\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"af\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8795672437739454\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"cs\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8609036905285453\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"ts\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8606005254677029\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"id\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8581523354418334\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"tr\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8568050032930087\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"et\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8549197675892446\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"sk\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8544002086788499\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"sn\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8528310247829071\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"bs\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8511972134585609\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"tn\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.849392007716692\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"hu\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8466935945564859\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"sl\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8382855781338786\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"hr\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8352499374265115\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"lt\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8340436692835289\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"vi\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8293260094733644\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"eu\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8268214716969057\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"lg\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.822968069617613\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"pt\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8221799621141127\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"pl\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8167154191441437\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"ca\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8141294839155507\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"is\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8094195515223185\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"ga\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8085492981527154\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"so\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8049966641212399\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"ro\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7934010406240615\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"ms\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7709323956726264\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"fi\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.770205810403696\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"lv\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7700872426062456\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"az\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7407095946436237\"^^<http://www.w3.org/2001/XMLSchema#double> ] ]\"^^<http://www.w3.org/2001/XMLSchema#string>");
                assertThat(aResult).isExhausted();
            }
    }

    @Test
    public void scoreAllExceptEnglishWithGermanPhrase() {

        final String aQuery = LanguageVocabulary.sparqlPrefix("lang") +
                " " + ArrayVocabulary.sparqlPrefix("array") +
                "select ?result where { bind(array:toString(lang:scoreFromAllExcept(\"guten morgan\", \"english\")) AS ?result) }";

        try (final SelectQueryResult aResult = connection.select(aQuery).execute()) {

            assertThat(aResult).hasNext();
            final Optional<Value> aPossibleValue = aResult.next().value("result");
            assertThat(aPossibleValue).isPresent();
            final Value aValue = aPossibleValue.get();
            assertThat(assertStringLiteral(aValue));
            final Literal aLiteral = ((Literal)aValue);
            assertThat(aLiteral.toString()).isEqualTo("\"[ [ \"nn\"^^<http://www.w3.org/2001/XMLSchema#string> \"1.0\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"nb\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9858691153271076\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"eu\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9850293027006409\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"nl\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9497061152873366\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"st\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9367621827560825\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"eo\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9361588546527195\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"sv\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9331926997875916\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"es\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9204195799995408\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"fr\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9200087563850966\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"la\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9155009748652377\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"sq\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9130582302155041\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"it\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.9105545868435218\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"ca\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8973527265540688\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"sn\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8960859074314828\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"bs\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8950505901956542\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"ro\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8938661670713922\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"da\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8903838441238028\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"pt\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8889065984039481\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"en\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8821269760636103\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"hr\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.877944987059758\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"lt\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8767054367084398\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"sk\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8761250301303085\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"id\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8753863475006456\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"fi\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8749877879126932\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"cs\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8697616472104578\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"pl\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8623191302884786\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"lv\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8618603181464308\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"cy\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8609584594143435\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"sl\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8543892304124014\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"zu\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8483146388888866\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"et\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8453705591929709\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"is\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8427035287629845\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"af\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.841477672527573\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"tl\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8393801154369833\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"lg\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8372148797126547\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"tr\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8329713216154397\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"ts\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8288890019907497\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"ms\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.8172469859573264\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"xh\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7990128234230236\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"hu\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7864199679765843\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"ga\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7688898980578478\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"yo\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7630444404178315\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"so\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.7527633985822354\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"sw\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.710173711584045\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"tn\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.6894113637127239\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"vi\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.6772505034798862\"^^<http://www.w3.org/2001/XMLSchema#double> ] [ \"az\"^^<http://www.w3.org/2001/XMLSchema#string> \"0.6620642198800443\"^^<http://www.w3.org/2001/XMLSchema#double> ] ]\"^^<http://www.w3.org/2001/XMLSchema#string>");
            assertThat(aResult).isExhausted();
        }
    }

    @Test
    public void testTooFewArgs() {
    
        final String aQuery = LanguageVocabulary.sparqlPrefix("lang") +
                    "select ?result where { bind(lang:scoreFromAllExcept() as ?result) }";

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
                    "select ?result where { bind(lang:scoreFromAllExcept(\"one\", \"two\") as ?result) }";

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
                    "select ?result where { bind(lang:scoreFromAllExcept(1) as ?result) }";

            try(final SelectQueryResult aResult = connection.select(aQuery).execute()) {
     
                assertThat(aResult.hasNext()).isTrue();

                final BindingSet aBindingSet = aResult.next();

                assertThat(aBindingSet.size()).isZero();
                assertThat(aResult.hasNext()).isFalse();
            }
    }
}
