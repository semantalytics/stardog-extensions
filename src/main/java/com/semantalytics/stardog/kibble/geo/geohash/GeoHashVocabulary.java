package com.semantalytics.stardog.kibble.geo.geohash;

import com.stardog.stark.IRI;
import com.stardog.stark.Values;

public enum GeoHashVocabulary {

    right,
    left,
    top,
    bottom,
    decode,
    encode,
    latitude,
    hashLengthToCoverBoundingBox,
    hashContains,
    heightDegrees,
    longitude,
    neighbors,
    widthDegrees,
    addLongitude;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/geo/hash/";
    public final IRI iri;

    GeoHashVocabulary() {
        iri = Values.iri(NAMESPACE, name());
    }

    public static String sparqlPrefix(String prefixName) {
        return "PREFIX " + prefixName + ": <" + NAMESPACE + "> ";
    }

    public String stringValue() {
        return iri.toString();
    }
}
