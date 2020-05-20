package com.semantalytics.stardog.kibble.json;

import com.stardog.stark.IRI;
import com.stardog.stark.Values;

public enum JsonVocabulary {

    path,
    keys;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/json/";
    public final IRI iri;

    JsonVocabulary() {
        iri = Values.iri(NAMESPACE, name());
    }

    public String stringValue() {
        return iri.toString();
    }
}
