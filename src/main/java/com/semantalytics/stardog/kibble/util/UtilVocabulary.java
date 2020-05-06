package com.semantalytics.stardog.kibble.util;

import com.stardog.stark.IRI;
import com.stardog.stark.Values;

public enum UtilVocabulary {

    ordinalize,
    pluralize,
    bindPrev,
    isValidXsdLiteral,
    proquint,
    sayNumericOrdinal,
    sayNumber,
    sayOrdinal,
    sayTime,
    sleep,
    slugify,
    slugifyCaseInsensitive,
    stardogVersion,
    dateTimeFormat,
    fromSpokenTime,
    timeFromNow,
    user;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/util/";
    public final IRI iri;

    UtilVocabulary() {
        iri = Values.iri(NAMESPACE, name());
    }

    public String stringValue() {
        return iri.toString();
    }
}
