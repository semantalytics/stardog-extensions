package com.semantalytics.stardog.kibble.string.phonetic;

import com.stardog.stark.IRI;

import static com.stardog.stark.Values.iri;

public class PhoneticVocabulary {

    public static final String NS = "http://semantalytics.com/2017/09/ns/stardog/kibble/string/phonetic/";

	public static final IRI carverphone2 = iri(NS + "carverphone2");
	public static final IRI colognePhonetic = iri(NS + "colognePhonetic");
	public static final IRI daitchMokotoffSoundex = iri(NS + "daitchMokotoffSoundex");
	public static final IRI doubleMetaphone = iri(NS + "doubleMetaphone");
	public static final IRI metaphone = iri(NS + "metaphone");
	public static final IRI nysiis = iri(NS + "nysiis");
	public static final IRI refinedSoundex = iri(NS + "refinedSoundex");
	public static final IRI soundex = iri(NS + "soundex");

    public static String sparqlPrefix(String prefixName) {
        return "PREFIX " + prefixName + ": <" + NS + "> ";
    }
}
