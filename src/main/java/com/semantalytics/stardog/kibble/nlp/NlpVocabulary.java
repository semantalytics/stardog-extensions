package com.semantalytics.stardog.kibble.nlp;

import com.complexible.common.rdf.model.StardogValueFactory;
import org.openrdf.model.IRI;

public enum NlpVocabulary {

    heidelTime;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/nlp/";
    public final IRI iri;

    NlpVocabulary() {
        iri = StardogValueFactory.instance().createIRI(NAMESPACE, name());
    }

    public String stringValue() {
        return iri.stringValue();
    }
}
