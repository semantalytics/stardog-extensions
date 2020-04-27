package com.semantalytics.stardog.kibble.string.unescape;

import com.complexible.common.rdf.model.StardogValueFactory;
import org.openrdf.model.IRI;

public enum UnescapeVocabulary {

    csv,
    ecmaScript,
    html3,
    html4,
    java,
    json,
    xml,
    xml10,
    xml11,
    xsi;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/string/unescape/";
    public final IRI iri;

    UnescapeVocabulary() {
        iri = StardogValueFactory.instance().createIRI(NAMESPACE, name());
    }

    public static String sparqlPrefix(String prefixName) {
        return "PREFIX " + prefixName + ": <" + NAMESPACE + "> ";
    }

    public String stringValue() {
        return iri.stringValue();
    }
}
