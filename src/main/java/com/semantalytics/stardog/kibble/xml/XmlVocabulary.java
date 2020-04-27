package com.semantalytics.stardog.kibble.xml;

import com.complexible.common.rdf.model.StardogValueFactory;
import org.openrdf.model.IRI;

public enum XmlVocabulary {

    xPath;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/xml/";
    public final IRI iri;

    XmlVocabulary() {
        iri = StardogValueFactory.instance().createIRI(NAMESPACE, name());
    }

    public String stringValue() {
        return iri.stringValue();
    }
}
