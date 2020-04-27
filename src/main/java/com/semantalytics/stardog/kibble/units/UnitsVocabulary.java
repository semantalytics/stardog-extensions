package com.semantalytics.stardog.kibble.units;

import com.complexible.common.rdf.model.StardogValueFactory;
import org.openrdf.model.IRI;

public enum UnitsVocabulary {

    ;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/units/";
    public final IRI iri;

    UnitsVocabulary() {
        iri = StardogValueFactory.instance().createIRI(NAMESPACE, name());
    }

    public String stringValue() {
        return iri.stringValue();
    }
}
