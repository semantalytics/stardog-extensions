package com.semantalytics.stardog.kibble.net.internetdomainname;

import com.complexible.common.rdf.model.StardogValueFactory;
import org.openrdf.model.IRI;

public enum InternetDomainNameVocabulary {

    hasParent,
    child,
    hasPublicSuffix,
    hasRegistrySuffix,
    isPublicSuffix,
    isRegistrySuffix,
    isTopDomainUnderRegistrySuffix,
    isTopPrivateDomain,
    isUnderPublicSuffix,
    isUnderRegistrySuffix,
    isValid,
    publicSuffix,
    registrySuffix,
    topDomainUnderRegistrySuffix,
    topPrivateDomain;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/net/internet-domain-name/";
    public final IRI iri;

    InternetDomainNameVocabulary() {
        iri = StardogValueFactory.instance().createIRI(NAMESPACE, name());
    }

    public static String sparqlPrefix(String prefixName) {
        return "PREFIX " + prefixName + ": <" + NAMESPACE + "> ";
    }

    public String stringValue() {
        return iri.stringValue();
    }
}
