package com.semantalytics.stardog.kibble.date;

import com.stardog.stark.IRI;
import com.stardog.stark.Values;

public enum DateVocabulary {

    epochTime,
    nextQuarter,
    previousQuarter,
    quarter,
    parseDate;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/date/";
    public final IRI iri;

    DateVocabulary() {
        iri = Values.iri(NAMESPACE, name());
    }

    public String stringValue() {
        return iri.toString();
    }
}
