package com.semantalytics.stardog.kibble.string.unescape;

import com.stardog.stark.IRI;

import static com.stardog.stark.Values.iri;

public class UnescapeVocabulary {

    public static final String NS = "http://semantalytics.com/2017/09/ns/stardog/kibble/string/unescape/";

	public static final IRI csv = iri(NS + "csv");
	public static final IRI ecmaScript = iri(NS + "ecmaScript");
	public static final IRI html3 = iri(NS + "html3");
	public static final IRI html4 = iri(NS + "html4");
	public static final IRI java = iri(NS + "java");
	public static final IRI json = iri(NS + "json");
	public static final IRI xml = iri(NS + "xml");
	public static final IRI xml10 = iri(NS + "xml10");
	public static final IRI xml11 = iri(NS + "xml11");
	public static final IRI xsi = iri(NS + "xsi");

    public static String sparqlPrefix(String prefixName) {
        return "PREFIX " + prefixName + ": <" + NS + "> ";
    }
}
