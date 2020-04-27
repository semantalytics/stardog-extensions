package com.semantalytics.stardog.kibble.email;

import com.complexible.common.rdf.model.StardogValueFactory;
import org.openrdf.model.IRI;

public enum EmailVocabulary {

    ;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/email/";
    public final IRI iri;

    EmailVocabulary() {
        iri = StardogValueFactory.instance().createIRI(NAMESPACE, name());
    }

    public String stringValue() {
        return iri.stringValue();
    }
}
