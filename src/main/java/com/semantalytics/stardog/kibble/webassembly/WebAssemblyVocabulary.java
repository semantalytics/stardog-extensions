package com.semantalytics.stardog.kibble.webassembly;

import com.stardog.stark.IRI;

import static com.stardog.stark.Values.iri;

public class WebAssemblyVocabulary {

	public static final String NS = "http://semantalytics.com/2017/09/ns/stardog/kibble/webassembly/";

	public static final IRI exec = iri(NS + "exec");

    public static String sparqlPrefix(final String prefixName) {
        return "PREFIX " + prefixName + ": <" + NS + "> ";
    }
}
