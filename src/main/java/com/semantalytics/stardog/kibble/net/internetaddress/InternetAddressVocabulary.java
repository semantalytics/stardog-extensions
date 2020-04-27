package com.semantalytics.stardog.kibble.net.internetaddress;

import com.complexible.common.rdf.model.StardogValueFactory;
import org.openrdf.model.IRI;

public enum InternetAddressVocabulary {

    toNumber,
    toAddress,
    isIp4MappedAddress,
    isLinkLocal,
    isValid,
    isValidIp6,
    isValidIp4;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/net/internetaddress/";
    public final IRI iri;

    InternetAddressVocabulary() {
        iri = StardogValueFactory.instance().createIRI(NAMESPACE, name());
    }

    public static String sparqlPrefix(String prefixName) {
        return "PREFIX " + prefixName + ": <" + NAMESPACE + "> ";
    }

    public String stringValue() {
        return iri.stringValue();
    }
}
