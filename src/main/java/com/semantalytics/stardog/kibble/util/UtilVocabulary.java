package com.semantalytics.stardog.kibble.util;

import com.stardog.stark.IRI;
import com.stardog.stark.Values;

public enum UtilVocabulary {

    ordinalize,
    pluralize,
    call,
    bindPrev,
    isValidXsdLiteral,
    proquint,
    randomProquint,
    sayNumericOrdinal,
    sayNumber,
    sayOrdinal,
    sayDuration,
    sayTime,
    sleep,
    slugify,
    slugifyCaseInsensitive,
    stardogVersion,
    dateTimeFormat,
    fromSpokenTime,
    timeFromNow,
    not,
    is,
    user;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/util/";
    public final IRI iri;

    UtilVocabulary() {
        iri = Values.iri(NAMESPACE, name());
    }

    public static String sparqlPrefix(String prefixName) {
        return "PREFIX " + prefixName + ": <" + NAMESPACE + "> ";
    }

    public String stringValue() {
        return iri.toString();
    }
}
