package com.semantalytics.stardog.kibble.json;

import com.complexible.common.rdf.model.StardogValueFactory;
import org.openrdf.model.IRI;

public enum JsonVocabulary {

    path;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/json/";
    public final IRI iri;

    JsonVocabulary() {
        iri = StardogValueFactory.instance().createIRI(NAMESPACE, name());
    }

    public String stringValue() {
        return iri.stringValue();
    }
}
