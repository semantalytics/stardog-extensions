package com.semantalytics.stardog.kibble.json;

import com.stardog.stark.IRI;

import static com.stardog.stark.Values.iri;

public class JsonVocabulary {

    public static final String NS = "http://semantalytics.com/2017/09/ns/stardog/kibble/json/";

    public static final IRI path = iri(NS + "path");
    public static final IRI keys = iri(NS + "keys");
    public static final IRI array = iri(NS + "array");
    public static final IRI arrayAppend = iri(NS + "array");
    public static final IRI jsonDataType = iri(NS + "jsonDataType");
    public static final IRI bsonDataType = iri(NS + "bsonDataType");

    public static String sparqlPrefix(final String prefixName) {
        return "PREFIX " + prefixName + ": <" + NS + "> ";
    }
}
