package com.semantalytics.stardog.kibble.date;

import com.complexible.common.rdf.model.StardogValueFactory;
import org.openrdf.model.IRI;

public enum DateVocabulary {

    epochTime,
    nextQuarter,
    previousQuarter,
    quarter,
    parseDate;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/date/";
    public final IRI iri;

    DateVocabulary() {
        iri = StardogValueFactory.instance().createIRI(NAMESPACE, name());
    }

    public String stringValue() {
        return iri.stringValue();
    }
}
