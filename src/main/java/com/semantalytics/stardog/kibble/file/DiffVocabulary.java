package com.semantalytics.stardog.kibble.file;

import com.stardog.stark.IRI;
import com.stardog.stark.Values;

public enum DiffVocabulary {

    diff,
    ;

    public static final String NAMESPACE = "http://semantalytics.com/2019/10/ns/stardog/kibble/diff/";
    public final IRI iri;

    DiffVocabulary() {
        iri = Values.iri(NAMESPACE, name());
    }

    public static String sparqlPrefix(String prefixName) {
        return "PREFIX " + prefixName + ": <" + NAMESPACE + "> ";
    }

    @Override
    public String toString() {
        return iri.toString();
    }
}
