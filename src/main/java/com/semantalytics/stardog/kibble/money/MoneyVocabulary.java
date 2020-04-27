package com.semantalytics.stardog.kibble.money;

import com.complexible.common.rdf.model.StardogValueFactory;
import org.openrdf.model.IRI;

public enum MoneyVocabulary {

    subtract;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/money/";
    public final IRI iri;

    MoneyVocabulary() {
        iri = StardogValueFactory.instance().createIRI(NAMESPACE, name());
    }

    public String stringValue() {
        return iri.stringValue();
    }
}
