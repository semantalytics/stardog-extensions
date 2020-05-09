package com.semantalytics.stardog.kibble.money;

import com.stardog.stark.IRI;
import com.stardog.stark.Values;

public enum MoneyVocabulary {

    subtract;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/money/";
    public final IRI iri;

    MoneyVocabulary() {
        iri = Values.iri(NAMESPACE, name());
    }

    public String stringValue() {
        return iri.toString();
    }
}
