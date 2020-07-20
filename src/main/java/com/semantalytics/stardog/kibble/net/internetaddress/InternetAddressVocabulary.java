package com.semantalytics.stardog.kibble.net.internetaddress;

import com.stardog.stark.IRI;

import static com.stardog.stark.Values.iri;

public class InternetAddressVocabulary {

    public static final String NS = "http://semantalytics.com/2017/09/ns/stardog/kibble/net/internetaddress/";

	public static final IRI toNumber = iri(NS + "toNumber");
	public static final IRI toAddress = iri(NS + "toAddress");
	public static final IRI isIp4MappedAddress = iri(NS + "isIp4MappedAddress");
	public static final IRI isUriInetAddress = iri(NS + "isUriInetAddress");
	public static final IRI isLinkLocalAddress = iri(NS + "isLinkLocalAddress");
	public static final IRI isValid = iri(NS + "isValid");
	public static final IRI isInetAddress = iri(NS + "isInetAddress");
	public static final IRI isValidIp6 = iri(NS + "isValidIp6");
	public static final IRI isValidIp4 = iri(NS + "isValidIp4");
	public static final IRI convertDottedQuadToHex = iri(NS + "convertDottedQuadToHex");
	public static final IRI ipv4AddressDt = iri(NS + "ipv4AddressDt");
	public static final IRI ipv6AddressDt = iri(NS + "ipv6AddressDt");
	public static final IRI fromInteger = iri(NS + "fromInteger");
	public static final IRI isMaximum = iri(NS + "isMaximum");
	public static final IRI getCompatIpV4Address = iri(NS + "getCompatIpV4Address");
	public static final IRI isMCGlobal = iri(NS + "isMCGlobal");
	public static final IRI isMCLinkLocal = iri(NS + "isMCLinkLocal");
	public static final IRI isMCNodeLocal = iri(NS + "isMCNodeLocal");
	public static final IRI isMCOrgLocal = iri(NS + "isMCOrgLocal");
	public static final IRI isMCSiteLocal = iri(NS + "isMCSiteLocal");
	public static final IRI isMulticastAddress = iri(NS + "isMulticastAddress");
	public static final IRI isSiteLocalAddress = iri(NS + "isSiteLocalAddress");
	public static final IRI isTeredoAddress = iri(NS + "isTeredoAddress");
	public static final IRI getLocalhost = iri(NS + "getLocalhost");
	public static final IRI isIsatapAddress = iri(NS + "isIsatapAddress");
	public static final IRI isCompatibleIp4Address = iri(NS + "isCompatibleIp4Address");
	public static final IRI hasEmbeddedIp4ClientAddress = iri(NS + "hasEmbeddedIp4ClientAddress");
	public static final IRI coerceToInteger = iri(NS + "coerceToInteger");
	public static final IRI decrement = iri(NS + "decrement");
	public static final IRI get6to4IPv4Address = iri(NS + "get6ito4IPv4Address");
	public static final IRI getAllByName = iri(NS + "getAllByName");
	public static final IRI getByName = iri(NS + "getByName");

    public static String sparqlPrefix(String prefixName) {
        return "PREFIX " + prefixName + ": <" + NS + "> ";
    }
}
