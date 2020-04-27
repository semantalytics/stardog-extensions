package com.semantalytics.stardog.kibble.javascript;

import com.complexible.common.rdf.model.StardogValueFactory;
import org.openrdf.model.IRI;

public enum JavascriptVocabulary {

    exec;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/javascript/";
    public final IRI iri;

    JavascriptVocabulary() {
        iri = StardogValueFactory.instance().createIRI(NAMESPACE, name());
    }

    public static String sparqlPrefix(String prefixName) {
        return "PREFIX " + prefixName + ": <" + NAMESPACE + "> ";
    }

    public String stringValue() {
        return iri.stringValue();
    }
}
