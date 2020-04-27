package com.semantalytics.stardog.kibble.quartz;

import com.complexible.common.rdf.model.StardogValueFactory;
import org.openrdf.model.IRI;

public enum QuartzVocabulary {

    ;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/quarts/";
    public final IRI iri;

    QuartzVocabulary() {
        iri = StardogValueFactory.instance().createIRI(NAMESPACE, name());
    }

    public String stringValue() {
        return iri.stringValue();
    }
}
