package com.semantalytics.stardog.kibble.net.internetaddress;

import com.stardog.stark.IRI;

import static com.stardog.stark.Values.iri;

public class InternetAddressVocabulary {

    public static final String NS = "http://semantalytics.com/2017/09/ns/stardog/kibble/net/internetaddress/";

	public static final IRI toNumber = iri(NS + "toNumber");
	public static final IRI toAddress = iri(NS + "toAddress");
	public static final IRI isIp4MappedAddress = iri(NS + "isIp4MappedAddress");
	public static final IRI isUriInetAddress = iri(NS + "isUriInetAddress");
	public static final IRI isLinkLocal = iri(NS + "isLinkLocal");
	public static final IRI isValid = iri(NS + "isValid");
	public static final IRI isValidIp6 = iri(NS + "isValidIp6");
	public static final IRI isValidIp4 = iri(NS + "isValidIp4");

    public static String sparqlPrefix(String prefixName) {
        return "PREFIX " + prefixName + ": <" + NS + "> ";
    }
}
