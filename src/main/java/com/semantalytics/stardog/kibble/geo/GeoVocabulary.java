package com.semantalytics.stardog.kibble.geo;

import com.stardog.stark.IRI;
import com.stardog.stark.Values;

public enum GeoVocabulary {

    toHoursMinutesSeconds;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/geo/";
    public final IRI iri;

    GeoVocabulary() {
        iri = Values.iri(NAMESPACE, name());
    }

    public static String sparqlPrefix(String prefixName) {
        return "PREFIX " + prefixName + ": <" + NAMESPACE + "> ";
    }

    public String stringValue() {
        return iri.toString();
    }
}
