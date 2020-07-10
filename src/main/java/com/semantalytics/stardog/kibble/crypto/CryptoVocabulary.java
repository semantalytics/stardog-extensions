package com.semantalytics.stardog.kibble.crypto;

import com.stardog.stark.IRI;

import static com.stardog.stark.Values.iri;

public class CryptoVocabulary {

    public static final String NS = "http://semantalytics.com/2017/09/ns/stardog/kibble/crypto/";

	public static final IRI formatPreservingEncrypt = iri(NS + "formatPreservingEncrypt");
	public static final IRI formatPreservingDecrypt = iri(NS + "formatPreservingDecrypt");

    public static String sparqlPrefix(String prefixName) {
        return "PREFIX " + prefixName + ": <" + NS + "> ";
    }
}
