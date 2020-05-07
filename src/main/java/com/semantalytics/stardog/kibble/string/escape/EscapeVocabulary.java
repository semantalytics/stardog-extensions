package com.semantalytics.stardog.kibble.string.escape;

import com.stardog.stark.IRI;
import com.stardog.stark.Values;

public enum EscapeVocabulary {

    csv,
    ecmaScript,
    html3,
    html4,
    java,
    json,
    xml10,
    xml11,
    xsi;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/string/escape/";
    public final IRI iri;

    EscapeVocabulary() {
        iri = Values.iri(NAMESPACE, name());
    }

    public static String sparqlPrefix(String prefixName) {
        return "PREFIX " + prefixName + ": <" + NAMESPACE + "> ";
    }

    public String stringValue() {
        return iri.toString();
    }
}
