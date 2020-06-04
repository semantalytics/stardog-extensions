package com.semantalytics.stardog.kibble.array;

import com.stardog.stark.IRI;
import com.stardog.stark.Values;

public enum ArrayVocabulary {

    append,
    cartesianProduct,
    concat,
    contains,
    distinct,
    difference,
    uniformDatatype,
    equals,
    fill,
    flatten,
    generate,
    index,
    indexOf,
    indexOfFirst,
    indexOfLast,
    intersection,
    isUniformType,
    isUniformLiteral,
    isContainedBy,
    isEmpty,
    intervalDay,
    nDims,
    notEquals,
    partition,
    size,
    shuffle,
    swap,
    shift,
    removeFirst,
    removeLast,
    removeAll,
    sub,
    of,
    offset,
    offsetDatatype,
    ordinal,
    ordinalDatatype,
    reverse,
    toString,
    zip,
    zipWithIndex;

    public static final String NAMESPACE = "http://semantalytics.com/2017/09/ns/stardog/kibble/array/";
    public final IRI iri;

    ArrayVocabulary() {
        iri = Values.iri(NAMESPACE, name());
    }

    public static String sparqlPrefix(String prefixName) {
        return "PREFIX " + prefixName + ": <" + NAMESPACE + "> ";
    }

    public String toString() {
        return iri.toString();
    }
}
