package com.semantalytics.stardog.kibble.string.phonetic;

import com.stardog.stark.IRI;
import com.stardog.stark.Values;

public enum PhoneticVocabulary {

    carverphone2,
    colognePhonetic,
    daitchMokotoffSoundex,
    doubleMetaphone,
    metaphone,
    nysiis,
    refinedSoundex,
    soundex;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/string/phonetic/";
    public final IRI iri;


    PhoneticVocabulary() {
        iri = Values.iri(NAMESPACE, name());
    }

    public static String sparqlPrefix(String prefixName) {
        return "PREFIX " + prefixName + ": <" + NAMESPACE + "> ";
    }

    public String stringValue() {
        return iri.toString();
    }
}
