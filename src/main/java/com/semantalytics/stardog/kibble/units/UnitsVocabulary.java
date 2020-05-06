package com.semantalytics.stardog.kibble.units;

import com.stardog.stark.IRI;
import com.stardog.stark.Values;

public enum UnitsVocabulary {

    ;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/units/";
    public final IRI iri;

    UnitsVocabulary() {
        iri = Values.iri(NAMESPACE, name());
    }

    public String stringValue() {
        return iri.toString();
    }
}
