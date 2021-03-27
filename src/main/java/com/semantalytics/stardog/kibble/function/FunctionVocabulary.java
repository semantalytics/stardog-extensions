package com.semantalytics.stardog.kibble.function;

import com.stardog.stark.IRI;

import static com.stardog.stark.Values.iri;

public class FunctionVocabulary {

	public static final String NS = "http://semantalytics.com/2017/09/ns/stardog/kibble/function/";

	public static final IRI call = iri(NS + "call");
    public static final IRI memoize = iri(NS + "memoize");
    public static final IRI compose = iri(NS + "compose");
    public static final IRI reduce = iri(NS + "reduce");
    public static final IRI map = iri(NS + "map");
    public static final IRI filter = iri(NS + "filter");
    public static final IRI partial = iri(NS + "partial");
    public static final IRI var = iri(NS + "var");

    public static String sparqlPrefix(final String prefixName) {
        return "PREFIX " + prefixName + ": <" + NS + "> ";
    }
}
