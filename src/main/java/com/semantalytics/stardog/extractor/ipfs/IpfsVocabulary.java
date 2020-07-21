package com.semantalytics.stardog.extractor.ipfs;

import com.stardog.stark.IRI;

import static com.stardog.stark.Values.iri;

public class IpfsVocabulary {

	public static final String NS = "http://semantalytics.com/2017/09/ns/stardog/extractor/ipfs/";

	public static final IRI ipfs = iri(NS + "ipfs");

    public static String sparqlPrefix(final String prefixName) {
        return "PREFIX " + prefixName + ": <" + NS + "> ";
    }
}
