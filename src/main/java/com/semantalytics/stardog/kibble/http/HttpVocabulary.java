package com.semantalytics.stardog.kibble.http;

import com.stardog.stark.IRI;

import static com.stardog.stark.Values.iri;

public class HttpVocabulary {

    public static final String NS = "http://semantalytics.com/2017/09/ns/stardog/kibble/http/";

	public static final IRI get = iri(NS + "get");

    public static String sparqlPrefix(String prefixName) {
        return "PREFIX " + prefixName + ": <" + NS + "> ";
    }

}
