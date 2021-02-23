package com.semantalytics.stardog.kibble.lang;

import com.stardog.stark.IRI;

import static com.stardog.stark.Values.iri;

public class LanguageVocabulary {

    public static final String NS = "http://semantalytics.com/2017/09/ns/stardog/kibble/lang/";

	public static final IRI detect = iri(NS + "detect");
    public static final IRI detectableLanguages = iri(NS + "detectableLanguages");
    public static final IRI detectFrom = iri(NS + "detectFrom");
    public static final IRI scoreFrom = iri(NS + "scoreFrom");
    public static final IRI scoreFromAllExcept = iri(NS + "scoreFromAllExcept");
    public static final IRI detectFromAllWithArabicScript = iri(NS + "detectFromAllWithArabicScript");
    public static final IRI scoreFromAllWithArabicScript = iri(NS + "scoreFromAllWithArabicScript");
    public static final IRI scoreFromAllWithCyrillicScript = iri(NS + "scoreFromAllWithCyrillicScript");
    public static final IRI detectFromAllWithDevanagariScript = iri(NS + "detectFromAllWithDevanagariScript");
    public static final IRI scoreFromAllWithDevanagariScript = iri(NS + "scoreFromAllWithDevanagariScript");
    public static final IRI detectFromAllWithLatinScript = iri(NS + "detectFromAllWithLatinScript");
    public static final IRI scoreFromAllWithLatinScript = iri(NS + "scoreFromAllWithLatinScript");
    public static final IRI scoreFromAllSpoken = iri(NS + "scoreFromAllSpoken");
    public static final IRI text = iri(NS + "text");
    public static final IRI detectFromAllSpoken = iri(NS + "detectFromAllSpoken");
    public static final IRI detectFromAllWithCyrillicScript = iri(NS + "detectFromAllWithCyrillicScript");
    public static final IRI detectFromAllExcept = iri(NS + "detectFromAllExcept");
    public static final IRI language = iri(NS + "language");
    public static final IRI score = iri(NS + "score");

    public static String sparqlPrefix(String prefixName) {
        return "PREFIX " + prefixName + ": <" + NS + "> ";
    }

}
