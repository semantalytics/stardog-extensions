package com.semantalytics.stardog.kibble.xml;

import com.stardog.stark.IRI;
import com.stardog.stark.Values;

public enum XmlVocabulary {

    xPath;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/xml/";
    public final IRI iri;

    XmlVocabulary() {
        iri = Values.iri(NAMESPACE, name());
    }

    public String stringValue() {
        return iri.toString();
    }
}
