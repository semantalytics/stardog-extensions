package com.semantalytics.stardog.kibble.util;

import com.stardog.stark.IRI;

import static com.stardog.stark.Values.iri;

public class UtilsVocabulary {

    public static final String NS = "http://semantalytics.com/2017/09/ns/stardog/kibble/utils/";

	public static final IRI shortId = iri(NS + "shortId");

    public static String sparqlPrefix(final String prefixName) {
        return "PREFIX " + prefixName + ": <" + NS + "> ";
    }
}