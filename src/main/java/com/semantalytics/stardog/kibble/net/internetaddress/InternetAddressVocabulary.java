package com.semantalytics.stardog.kibble.net.internetaddress;

import com.stardog.stark.IRI;
import com.stardog.stark.Values;

public enum InternetAddressVocabulary {

    toNumber,
    toAddress,
    isIp4MappedAddress,
    isUriInetAddress,
    isLinkLocal,
    isValid,
    isValidIp6,
    isValidIp4;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/net/internetaddress/";
    public final IRI iri;

    InternetAddressVocabulary() {
        iri = Values.iri(NAMESPACE, name());
    }

    public static String sparqlPrefix(String prefixName) {
        return "PREFIX " + prefixName + ": <" + NAMESPACE + "> ";
    }

    public String stringValue() {
        return iri.toString();
    }
}
